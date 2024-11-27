import com.sharcar.domain.usecases.travel.AddPassengerToTravel
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import com.sharcar.domain.usecases.travel.SwapTravelToNewOne
import org.koin.dsl.module

val travelModule = module {
    includes(userRepositoryModule, inscriptionRepositoryModule)

    single { CreateTravelUsecase(get(), get(), get(), get()) }
    single { AddPassengerToTravel(get(), get()) }
    single { SwapTravelToNewOne(get(), get()) }
}