package br.com.starlog.model;

/**
 * CAMADA MODEL - Mensagem criptográfica (Cifra de César).
 * Versão com programação funcional (streams de caracteres) e validação centralizada.
 */
public class CriptoMensagem {
    private static final int ALFABETO = 26;
    private static final int CHAVE_MIN = 1;
    private static final int CHAVE_MAX = 25;

    private final String remetente;
    private final String mensagem;
    private final int chaveDeslocamento;

    public CriptoMensagem(String remetente, String mensagem, int chaveDeslocamento) {
        this.remetente = exigirTexto(remetente, "Remetente não pode ser nulo ou vazio.");
        this.mensagem = exigirTexto(mensagem, "Mensagem não pode ser nula ou vazia.");
        if (chaveDeslocamento < CHAVE_MIN || chaveDeslocamento > CHAVE_MAX) {
            throw new IllegalArgumentException("Chave deve estar entre 1 e 25.");
        }
        this.chaveDeslocamento = chaveDeslocamento;
    }

    private static String exigirTexto(String valor, String erro) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(erro);
        }
        return valor.strip().toUpperCase();
    }

    public String getRemetente() {
        return remetente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getChaveDeslocamento() {
        return chaveDeslocamento;
    }

    public String cifrar() {
        StringBuilder sb = new StringBuilder(mensagem.length());
        mensagem.chars()
                .map(this::rotacionar)
                .forEach(codigo -> sb.append((char) codigo));
        return sb.toString();
    }

    private int rotacionar(int c) {
        if (c < 'A' || c > 'Z') {
            return c; // espaços, números e pontuação passam intactos
        }
        return 'A' + Math.floorMod(c - 'A' + chaveDeslocamento, ALFABETO);
    }

    public int calcularChecksum() {
        return mensagem.chars().sum();
    }
}
