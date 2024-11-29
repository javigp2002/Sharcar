import com.sharcar.datasource.vehicle.vehicleDatasourceModule
import com.sharcar.domain.repository.vehicle.VehicleRepository
import com.sharcar.domain.repository.vehicle.VehicleRepositoryImpl
import org.koin.dsl.module

val vehicleRepositoryModule = module {
    includes(vehicleDatasourceModule)

    single<VehicleRepository> { VehicleRepositoryImpl(get()) }
}