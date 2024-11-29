import com.sharcar.datasource.inscription.inscriptionDatasourceModule
import com.sharcar.domain.repository.inscription.InscriptionRepository
import com.sharcar.domain.repository.inscription.InscriptionRepositoryImpl
import org.koin.dsl.module

val inscriptionRepositoryModule = module {
    includes(inscriptionDatasourceModule)

    single<InscriptionRepository> { InscriptionRepositoryImpl(get()) }
}