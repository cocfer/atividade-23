package br.com.starlog.controller;

import br.com.starlog.model.CriptoMensagem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** CAMADA CONTROLLER - orquestra View -> Model sem nenhuma dependência de Swing. */
public class TransmissaoController {

    private final List<String> bufferTransmissoes = new ArrayList<>();

    public String processarTransmissao(String remetenteRaw, String msgRaw, String chaveStr) {
        CriptoMensagem cripto = new CriptoMensagem(remetenteRaw, msgRaw, converterChave(chaveStr));
        String pacote = formatar(cripto);
        bufferTransmissoes.add(pacote);
        return pacote;
    }

    private int converterChave(String chaveStr) {
        try {
            return Integer.parseInt(chaveStr.trim());
        } catch (RuntimeException e) { // NumberFormatException ou NullPointerException
            throw new IllegalArgumentException("Chave deve ser um número inteiro válido.", e);
        }
    }

    private String formatar(CriptoMensagem c) {
        return "[" + c.getRemetente() + "] #" + c.calcularChecksum() + " -> " + c.cifrar();
    }

    public List<String> getBufferTransmissoes() {
        return Collections.unmodifiableList(bufferTransmissoes);
    }

    public int getTotalTransmissoes() {
        return bufferTransmissoes.size();
    }
}
