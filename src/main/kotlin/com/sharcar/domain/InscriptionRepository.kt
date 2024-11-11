package com.sharcar.domain
import com.sharcar.entities.Inscription

interface InscriptionRepository {
    fun save(inscription: Inscription): Inscription
}