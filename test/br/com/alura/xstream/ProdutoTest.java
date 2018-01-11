package br.com.alura.xstream;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ProdutoTest {

	@Test
	public void deveGerarOXmlComNomePrecoEDescricaoAdequados(){
		String xmlEsperado = "<produto codigo=\"777\">\n"+
		"  <nome>geladeira</nome>\n"+
		"  <preco>1000.0</preco>\n"+
		"  <descrição>Geladeira duas portas</descrição>\n"+
		"</produto>";
		
		
		Produto geladeira = new Produto("geladeira", 1000.0, "Geladeira duas portas", 777);
		
		XStream xstream = new XStream();
		xstream.alias("produto", Produto.class);
		xstream.aliasField("descrição", Produto.class, "descricao");
		xstream.useAttributeFor(Produto.class, "codigo");
		String xmlGerado = xstream.toXML(geladeira);
		
		assertEquals(xmlEsperado, xmlGerado);
	}
	
}
