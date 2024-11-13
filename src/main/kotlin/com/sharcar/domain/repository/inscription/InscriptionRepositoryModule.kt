import com.sharcar.domain.repository.inscription.InscriptionRepositoryImpl
import org.koin.dsl.module

val inscriptionRepositoryModule = module {
    single { InscriptionRepositoryImpl(get()) }
}