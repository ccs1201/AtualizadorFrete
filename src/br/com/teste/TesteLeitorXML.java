/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

import br.com.utils.exceptions.LeitorXmlException;
import br.com.utils.LeitorXMLImpl;
import java.util.Collection;
import java.util.Map;
import org.jdom2.Element;

/**
 *
 * @author Cleber
 */
public class TesteLeitorXML {

    public static void main(String[] args) throws LeitorXmlException {

        LeitorXMLImpl l = new LeitorXMLImpl();
        l.carregarXML("D:\\XML\\02\\NFE42130585200657000155550010001119861003901579.xml");

        Map<String, Element> map = l.getElementsMap();
        Collection<String> c = map.keySet();

        while (c.iterator().hasNext()) {

            int i = 0;
//            Element e = null;
//            e = map.get(c);
            System.out.print("Pos: " + i);
//            System.out.println(" Key: " + c + " Value: " + e.getValue());
            i++;
        }


    }
}
