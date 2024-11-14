import com.sharcar.domain.usecases.travel.AddPassengerToTravel
import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import org.koin.dsl.module

val travelModule = module {
    single { CreateTravelUsecase(get()) }
    single { AddPassengerToTravel(get(), get()) }
}