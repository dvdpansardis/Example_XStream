package br.com.alura.xstream;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.TreeMarshaller.CircularReferenceException;

public class CategoriaTeste {

	@Test(expected = CircularReferenceException.class)
	public void deveSerializarUmCiclo() {
		String xmlEsperado = "";

		Categoria esporte = new Categoria(null, "esporte");
		Categoria futebol = new Categoria(esporte, "esporte");
		Categoria geral = new Categoria(futebol, "geral");

		esporte.setPai(geral);

		XStream xstream = new XStream();
		xstream.alias("categoria", Categoria.class);
		//Recursive reference to parent object
		//xstream.setMode(XStream.NO_REFERENCES);
		xstream.setMode(XStream.NO_REFERENCES);
		String xmlGerado = xstream.toXML(esporte);

		assertEquals(xmlEsperado, xmlGerado);
	}

}


