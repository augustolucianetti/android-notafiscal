package augustolucianetti.com.br.notafiscalapplication.model

import java.io.Serializable

data class NotaFiscal (
    var numero: Int = 0,
    var nomeProduto: String = "",
    var quantidade: Float = Float.MIN_VALUE,
    var valor: Float = Float.MIN_VALUE,
    var entrada: Boolean = false,
    var saida: Boolean = false,
    var id: String = ""
) : Serializable { }