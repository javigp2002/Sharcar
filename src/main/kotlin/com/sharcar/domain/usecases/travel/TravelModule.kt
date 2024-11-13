import com.sharcar.domain.usecases.travel.CreateTravelUsecase
import org.koin.dsl.module

val travelModule = module {
    single { CreateTravelUsecase(get()) }
}