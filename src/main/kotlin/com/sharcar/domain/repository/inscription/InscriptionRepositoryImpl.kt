package com.sharcar.domain.repository.inscription

import EnterpriseDatasource
import com.sharcar.datasource.inscription.InscriptionDatasource
import com.sharcar.datasource.inscription.InscriptionEntity
import com.sharcar.datasource.location.LocationDatasource
import com.sharcar.datasource.user.UserDatasource
import com.sharcar.datasource.vehicle.VehicleDatasource
import com.sharcar.domain.usecases.model.InscriptionModel
import com.sharcar.entities.Inscription
import com.sharcar.entities.User

class InscriptionRepositoryImpl(
    private val datasource: InscriptionDatasource,
    private val userDatasource: UserDatasource,
    private val vehicleDatasource: VehicleDatasource,
    private val enterpriseDatasource: EnterpriseDatasource,
    private val locationsDatasource: LocationDatasource,
) : InscriptionRepository {
    override fun save(inscription: InscriptionModel): Inscription {
        return datasource.save(inscription)
    }

    override fun delete(inscriptionId: Int): Boolean {
        return datasource.delete(inscriptionId)
    }

    override fun updatePassengerIntoInscription(inscriptionId: Int, passenger: User): Boolean {
        val areSeatsAvailable = getSeatsAvailable(inscriptionId) > 0
        if (!areSeatsAvailable)
            return false


        return datasource.updatePassengerIntoInscription(inscriptionId, passenger)
    }

    override fun getInscriptionsOfEnterprises(enterpriseId: Int): MutableList<Inscription> {
        val inscriptionEntities = datasource.getInscriptionsOfEnterprises(enterpriseId)


        val inscriptionResult = inscriptionEntities.mapNotNull {
            inscriptionEntityToInscription(it)
        }

        return inscriptionResult.toMutableList()
    }

    override fun getSeatsAvailable(inscriptionId: Int): Int {
        val inscriptionEntity =
            datasource.getInscriptionById(inscriptionId) ?: throw IllegalArgumentException("Inscription not found")
        val vehicle = vehicleDatasource.findById(inscriptionEntity.vehicleId)
            ?: throw IllegalArgumentException("Vehicle not found")
        return vehicle.maxPassengers - datasource.getIdsNumberPassengers(inscriptionId).size
    }

    override fun getInscriptionById(inscriptionId: Int): Inscription? {
        val inscriptionEntity = datasource.getInscriptionById(inscriptionId) ?: return null

        return inscriptionEntityToInscription(inscriptionEntity)

    }

    private fun inscriptionEntityToInscription(inscriptionEntity: InscriptionEntity): Inscription? {
        return try {
            Inscription(
                id = inscriptionEntity.id,
                enterprise = enterpriseDatasource.findById(inscriptionEntity.enterpriseId)
                    ?: throw IllegalArgumentException("Enterprise not found"),
                departureTime = inscriptionEntity.departureTime,
                departurePlace = inscriptionEntity.departurePlace,
                arrivalPlace = locationsDatasource.findById(inscriptionEntity.arrivalPlaceId)
                    ?: throw IllegalArgumentException("Arrival place unexistent"),
                driver = userDatasource.findByEmail(inscriptionEntity.driverEmail)
                    ?: throw IllegalArgumentException("Driver not found"),
                vehicle = vehicleDatasource.findById(inscriptionEntity.vehicleId)
                    ?: throw IllegalArgumentException("Vehicle not found"),
                passengers = datasource.getIdsNumberPassengers(inscriptionEntity.id)
                    .mapNotNull { userDatasource.findByEmail(it) }.toMutableList()
            )
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}