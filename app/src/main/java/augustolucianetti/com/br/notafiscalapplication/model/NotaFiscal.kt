package augustolucianetti.com.br.notafiscalapplication.model

import java.io.Serializable

data class NotaFiscal (
    val numero: Int = 0,
    val nomeProduto: String = "",
    val quantidade: Float = Float.MIN_VALUE,
    val valor: Float = Float.MIN_VALUE,
    val entrada: Boolean = false,
    val saida: Boolean = false,
    val id: String = ""
) : Serializable { }