import com.sharcar.datasource.enterprise.enterpriseDatasourceModule
import com.sharcar.datasource.inscription.inscriptionDatasourceModule
import com.sharcar.datasource.location.locationDatasourceModule
import com.sharcar.datasource.user.userDatasourceModule
import com.sharcar.datasource.vehicle.vehicleDatasourceModule
import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.repository.inscription.InscriptionRepositoryImpl
import org.koin.dsl.module

val inscriptionRepositoryModule = module {
    includes(
        inscriptionDatasourceModule, userDatasourceModule, vehicleDatasourceModule, enterpriseDatasourceModule,
        locationDatasourceModule
    )

    single<InscriptionRepository> { InscriptionRepositoryImpl(get(), get(), get(), get(), get()) }
}