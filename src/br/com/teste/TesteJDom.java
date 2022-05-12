/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

import br.com.utils.exceptions.LeitorXmlException;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Cleber
 */
public class TesteJDom {

    private static String path = "D:\\XML\\Althis\\NFe42130413971092000107550010000010431000010427-procnfe.xml";
    private static SAXBuilder builder = new org.jdom2.input.SAXBuilder();

    public static void main(String[] args) throws LeitorXmlException {
        Element root;
        try {
            Document document = builder.build(path);
            root = document.getRootElement();

        } catch (JDOMException ex) {
            throw new LeitorXmlException("Erro ao abrir arquivo XML.", ex);
        } catch (IOException ex) {
            throw new LeitorXmlException("Erro ao abrir arquivo XML.", ex);
        }

        int nivel = 0;
        System.out.println("Root name:" + root.getName());
       
        //Namespace ns = Namespace.getNamespace("http://www.portalfiscal.inf.br/nfe");
        for (Element e : root.getChildren()) {
            
            
            
//            Element e1 = e.getChild("infNFe",root.getNamespace());
//            if(e1 != null){
//                System.out.println(e1.getName());
//            }
        }


    }

    private Element pegaFilhos(Element e) {
        if (temFilhos(e)) {
            pegaFilhos(e);
        }
        return e;
    }

    private boolean temFilhos(Element e) {
        if (e.getChildren().iterator().hasNext()) {
            return true;
        } else {
            return false;
        }
    }
}
