package com.sharcar.domain.repository.inscription

import EnterpriseDatasource
import com.sharcar.datasource.inscription.InscriptionDatasource
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
        return datasource.updatePassengerIntoInscription(inscriptionId, passenger)
    }

    override fun getInscriptionsOfEnterprises(enterpriseId: Int): MutableList<Inscription> {
        val inscriptionEntities = datasource.getInscriptionsOfEnterprises(enterpriseId)


        val inscriptionResult = inscriptionEntities.map {
            Inscription(
                id = it.id,
                enterprise = enterpriseDatasource.findById(enterpriseId)
                    ?: throw IllegalArgumentException("Enterprise not found"),
                departureTime = it.departureTime,
                departurePlace = it.departurePlace,
                arrivalPlace = locationsDatasource.findById(it.arrivalPlaceId)
                    ?: throw IllegalArgumentException("Arrival place unexistent"),
                driver = userDatasource.findByEmail(it.driverEmail)
                    ?: throw IllegalArgumentException("Driver not found"),
                vehicle = vehicleDatasource.findById(it.vehicleId)
                    ?: throw IllegalArgumentException("Vehicle not found"),
                passengers = mutableListOf()
            )
        }

        return inscriptionResult.toMutableList()
    }

    override fun getSeatsAvailable(inscriptionId: Int): Int {
        return datasource.getSeatsAvailable(inscriptionId)
    }

    override fun getInscriptionById(inscriptionId: Int): Inscription? {
        return datasource.getInscriptionById(inscriptionId)
    }
}