package augustolucianetti.com.br.notafiscalapplication.model

data class NotaFiscal (
    val numero: Number = 0,
    val nomeProduto: String = "",
    val quantidade: Float = Float.MIN_VALUE,
    val valor: Float = Float.MIN_VALUE,
    val entrada: Boolean = false,
    val saida: Boolean = false
)