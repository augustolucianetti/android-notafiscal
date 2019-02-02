package augustolucianetti.com.br.notafiscalapplication.model

import java.io.Serializable
import java.io.StringBufferInputStream
import java.math.BigInteger

data class NotaFiscal (
    var numero: Int = 0,
    var nomeProduto: String = "",
    var quantidade: Float = Float.MIN_VALUE,
    var valor: Float = Float.MIN_VALUE,
    var entrada: Boolean = false,
    var saida: Boolean = false,
    var armazenagem: Boolean = false,
    var cnpjFornecedorCliente: String = "",
    var id: String = "",
    var userId: String = ""
) : Serializable { }