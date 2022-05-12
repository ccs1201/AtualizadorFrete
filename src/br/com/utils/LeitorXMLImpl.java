package br.com.utils;

import br.com.dao.LeitorXML;
import br.com.utils.exceptions.LeitorXmlException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Cleber
 */
public class LeitorXMLImpl implements LeitorXML{

    private Document document = null;
    private final SAXBuilder builder = new org.jdom2.input.SAXBuilder();
    private Element ideElement;
    private Element emitElement;
    private Element destElement;
    private Element ICMSTotElement;
    private Element volElement;
    private Element root;

    @Override
    public void carregarXML(String path) throws LeitorXmlException {

        try {
            document = builder.build(path);
            this.root = document.getRootElement();
            this.getElements();
        } catch (JDOMException | IOException ex) {
            throw new LeitorXmlException("Erro ao abrir arquivo XML.", ex);
        }
    }

    /**
     * Retorna String CNPJ Emitente
     * @return 
     */
    @Override
    public String getEmitente() {

        for (Element e : emitElement.getChildren()) {
            if (e.getName().equals("CNPJ")) {
                return e.getValue();
            }
        }
        return null;
    }

    /*
     * Retorna String CNPJ destinatario
     */
    @Override
    public String getDestinatario() {
        for (Element e : destElement.getChildren()) {
            switch (e.getName()) {
                case "CNPJ":
                    return e.getValue();
                case "CPF":
                    return e.getValue();
            }
        }
        return null;
    }

    /*
     * Retorna double Valor da NF
     */
    @Override
    public double getValorDanfe() throws TrataErro {
        String strValor = this.getValor();

        if (strValor == null) {
            throw new TrataErro("Erro ao converter String Valor Danfe em inteiro.",
                    "Método this.getValor(); retornou null.");
        } else {
            try {
                return Double.parseDouble(strValor);
            } catch (NumberFormatException e) {
                throw new TrataErro("Erro ao recuperar valor da Danfe.", e.getMessage());
            }


        }
    }

    /*
     * Retorna String CNPJ Emitente
     */
    private String getCnpjEmit() {

        for (Element e : emitElement.getChildren()) {
            if (e.getName().equals("CNPJ")) {
                return e.getValue();
            }
        }
        return null;
    }

    //Retorna String valor total da Danfe
    private String getValor() {

        for (Element e : ICMSTotElement.getChildren()) {
            if (e.getName().equals("vNF")) {
                return e.getValue();
            }
        }
        return null;
    }

    @Override
    public int getVolumes() throws TrataErro {

        String strVolumes = this.getVols();
        if (strVolumes == null) {
            return 0;
        } else {
            try {
                int vols = Integer.parseInt(strVolumes);
                return vols;
            } catch (NumberFormatException e) {
                throw new TrataErro("Erro ao recuperar a quantidade de volumes."
                        + "Erro ao converter string em numero.", e.getMessage());
            }
        }
    }

    @Override
    public int getNumeroDanfe() throws TrataErro {

        String nDanfe = this.getNdanfe();
        try {
            int danfe = Integer.parseInt(nDanfe);
            return danfe;
        } catch (NumberFormatException e) {
            throw new TrataErro("Erro ao recuperar numero da Danfe.", e.getMessage());
        }
    }

    //Captura todos os elements necessarios para gerar o conhecimento
    private void getElements() {

        Element infNFeElement = this.getInfNFe();

        for (Element child : infNFeElement.getChildren()) {
            switch (child.getName()) {
                case "ide":
                    this.ideElement = child;
                    break;
                case "emit":
                    this.emitElement = child;
                    break;
                case "dest":
                    this.destElement = child;
                    break;
                case "total":
                    this.getICSMTot(child);
                    break;
                case "transp":
                    this.volElement = this.getVolElement(child);
                    break;
            }
        }
    }

    // Retorna String Volumes
    private String getVols() {

        //Se o volElement for null retorna zero.
        if (volElement != null) {
            for (Element e : volElement.getChildren()) {
                if (e.getName().equals("qVol")) {
                    return e.getValue();
                }
            }
        }
        return "0";
    }

    //Pega o child vol do nó Transp
    private Element getVolElement(Element transp) {

        for (Element e : transp.getChildren()) {
            if (e.getName().equals("vol")) {
                return e;
            }
        }
        return null;
    }

    //Pega o element ICMTot do no total
    private void getICSMTot(Element total) {

        for (Element e : total.getChildren()) {
            if (e.getName().equals("ICMSTot")) {
                ICMSTotElement = e;
            }
        }
    }

    //Retorna String com numero da Danfe
    private String getNdanfe() {

        for (Element e : ideElement.getChildren()) {
            if (e.getName().equals("nNF")) {
                return e.getValue();
            }
        }
        return null;
    }

    //Pega o child infNFe
    public Element getInfNFe() {

        Element nfe = this.getNFe();
        for (Element e : nfe.getChildren()) {
            if (e.getName().equals("infNFe")) {
                return e;
            }
        }
        return null;
    }

    //Retorna o element NFe do xml
    private Element getNFe() {
        List<Element> elements = root.getChildren();

        for (Element e : elements) {
            if (e.getName().equals("NFe")) {
                return e;
            }
        }
        return null;
    }

        public Map<String, Element> getElementsMap() throws LeitorXmlException {

        Map<String, Element> mapa = new LinkedHashMap<>();
        
        for (Element e : root.getChildren()) {
            this.pegarFilhos(e, mapa);
        }
        return mapa;
    }

    private void pegarFilhos(Element element, Map mapa) {
        for (Element e : element.getChildren()) {
            if (temFilhos(e)) {
                this.pegarFilhos(e, mapa);
            } else {
                mapa.put(e.getName(), e);
            }
        }
    }

    private boolean temFilhos(Element element) {
       return element.getChildren().iterator().hasNext();
    }
}
