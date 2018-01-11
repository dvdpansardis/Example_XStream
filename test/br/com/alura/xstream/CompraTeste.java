package br.com.alura.xstream;

import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class CompraTeste {

	@Test
	public void deveUtilizarUmConversorTotalmenteCustomizado() {
		StringBuilder xmlEsperado = new StringBuilder();
		xmlEsperado.append("<compra estilo=\"novo\">\n");
		xmlEsperado.append("  <id>15</id>\n");
		xmlEsperado.append("  <fornecedor>dvd@x.com</fornecedor>\n");
		xmlEsperado.append("  <endereco>\n");
		xmlEsperado.append("    <linha1>Rua Vergueiro 1000</linha1>\n");
		xmlEsperado.append("    <linha2>10 Andar</linha2>\n");
		xmlEsperado.append("  </endereco>\n");
		xmlEsperado.append("  <produtos>\n");
		xmlEsperado.append("    <produto codigo=\"1587\">\n");
		xmlEsperado.append("      <nome>geladeira</nome>\n");
		xmlEsperado.append("      <preco>1000.0</preco>\n");
		xmlEsperado.append("      <descrição>Geladeira duas portas</descrição>\n");
		xmlEsperado.append("    </produto>\n");
		xmlEsperado.append("    <produto codigo=\"1587\">\n");
		xmlEsperado.append("      <nome>geladeira</nome>\n");
		xmlEsperado.append("      <preco>1000.0</preco>\n");
		xmlEsperado.append("      <descrição>Geladeira duas portas</descrição>\n");
		xmlEsperado.append("    </produto>\n");
		xmlEsperado.append("  </produtos>\n");
		xmlEsperado.append("</compra>");

		List<Produto> produtos = new ArrayList<>();
		Compra compra = new Compra(15, produtos);

		produtos.add(geladeiraMock());
		produtos.add(geladeiraMock());

		XStream xstream = injectXStream();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.registerConverter(new CompraDiferenteConverter());

		String xmlGerado = xstream.toXML(compra);

		assertEquals(xmlEsperado.toString(), xmlGerado);
		
		Compra fromXML = (Compra) xstream.fromXML(xmlGerado);
		
		assertEquals(compra, fromXML);
	}
	
	@Test
	public void deveGerarDuasGeladeiras() {
		StringBuilder xmlEsperado = new StringBuilder();
		xmlEsperado.append("<compra>\n");
		xmlEsperado.append("  <id>15</id>\n");
		xmlEsperado.append("  <produtos>\n");
		xmlEsperado.append("    <produto codigo=\"1587\">\n");
		xmlEsperado.append("      <nome>geladeira</nome>\n");
		xmlEsperado.append("      <preco>1000.0</preco>\n");
		xmlEsperado.append("      <descrição>Geladeira duas portas</descrição>\n");
		xmlEsperado.append("    </produto>\n");
		xmlEsperado.append("    <produto codigo=\"1587\">\n");
		xmlEsperado.append("      <nome>geladeira</nome>\n");
		xmlEsperado.append("      <preco>1000.0</preco>\n");
		xmlEsperado.append("      <descrição>Geladeira duas portas</descrição>\n");
		xmlEsperado.append("    </produto>\n");
		xmlEsperado.append("  </produtos>\n");
		xmlEsperado.append("</compra>");

		List<Produto> produtos = new ArrayList<>();
		Compra compra = new Compra(15, produtos);

		produtos.add(geladeiraMock());
		produtos.add(geladeiraMock());

		XStream xstream = injectXStream();
		xstream.setMode(XStream.NO_REFERENCES);

		String xmlGerado = xstream.toXML(compra);

		assertEquals(xmlEsperado.toString(), xmlGerado);
	}

	@Test
	public void deveGerarCadaUmDosProdutosDoXml() {
		StringBuilder xml = new StringBuilder();
		xml.append("<compra>\n");
		xml.append("  <id>15</id>\n");
		xml.append("  <produtos class=\"linked-list\">\n");
		xml.append("    <produto codigo=\"1587\">\n");
		xml.append("      <nome>geladeira</nome>\n");
		xml.append("      <preco>1000.0</preco>\n");
		xml.append("      <descrição>Geladeira duas portas</descrição>\n");
		xml.append("    </produto>\n");
		xml.append("    <produto codigo=\"1588\">\n");
		xml.append("      <nome>ferro de passar</nome>\n");
		xml.append("      <preco>100.0</preco>\n");
		xml.append("      <descrição>Ferro com vaporizador</descrição>\n");
		xml.append("    </produto>\n");
		xml.append("  </produtos>\n");
		xml.append("</compra>");

		XStream xstream = injectXStream();

		Compra fromXML = (Compra) xstream.fromXML(xml.toString());

		List<Produto> produtosEsperado = new LinkedList<>();
		//addDefaultImplementation
		Compra compraEsperada = new Compra(15, produtosEsperado);

		produtosEsperado.add(geladeiraMock());
		produtosEsperado.add(ferroMock());

		assertEquals(compraEsperada, fromXML);
	}

	private XStream injectXStream() {
		XStream xstream = new XStream();
		xstream.alias("compra", Compra.class);
		xstream.alias("produto", Produto.class);
		xstream.aliasField("descrição", Produto.class, "descricao");
		xstream.useAttributeFor(Produto.class, "codigo");
		return xstream;
	}

	private Produto ferroMock() {
		return new Produto("ferro de passar", 100.0, "Ferro com vaporizador", 1588);
	}

	private Produto geladeiraMock() {
		return new Produto("geladeira", 1000.0, "Geladeira duas portas", 1587);
	}
	
	@Test
	public void converterDblToMoneyBR(){
		Locale Brasil = new Locale("pt", "br");
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Brasil);
		String formatado = formatter.format(1000.0);
		System.out.println(formatado);
	}

	@Test
	public void deveSerializarObjetosComHerança(){
		StringBuilder xmlEsperado = new StringBuilder();
		xmlEsperado.append("<compra>\n");
		xmlEsperado.append("  <id>15</id>\n");
		xmlEsperado.append("  <produtos>\n");
		xmlEsperado.append("    <livro codigo=\"1587\">\n");
		xmlEsperado.append("      <nome>Java Biblia</nome>\n");
		xmlEsperado.append("      <preco>R$ 1.000,00</preco>\n");
		xmlEsperado.append("      <descrição>Muito bom</descrição>\n");
		xmlEsperado.append("    </livro>\n");
		xmlEsperado.append("    <musica codigo=\"1588\">\n");
		xmlEsperado.append("      <nome>The number of the best</nome>\n");
		xmlEsperado.append("      <preco>R$ 100,00</preco>\n");
		xmlEsperado.append("      <descrição>IronMaiden</descrição>\n");
		xmlEsperado.append("    </musica>\n");
		xmlEsperado.append("  </produtos>\n");
		xmlEsperado.append("</compra>");

		List<Produto> produtos = new ArrayList<>();
		Compra compra = new Compra(15, produtos);

		produtos.add(livroMock());
		produtos.add(musicaMock());

		XStream xstream = injectXStream();
		xstream.alias("livro", Livro.class);
		xstream.alias("musica", Musica.class);
		//xstream.registerLocalConverter(Produto.class, "preco", new PrecoConverter());
		xstream.registerLocalConverter(Produto.class, "preco", new PrecoSimplesConverter());

		String xmlFromObject = xstream.toXML(compra);

		assertEquals(xmlEsperado.toString(), xmlFromObject);
	}
	
	private Produto musicaMock() {
		return new Musica("The number of the best", 100.0, "IronMaiden", 1588);
	}

	private Produto livroMock() {
		return new Livro("Java Biblia", 1000.0, "Muito bom", 1587);
	}

	@Test
	public void deveSerializarColecoesImplicitas(){
		StringBuilder xmlEsperado = new StringBuilder();
		xmlEsperado.append("<compra>\n");
		xmlEsperado.append("  <id>15</id>\n");
		xmlEsperado.append("  <produto codigo=\"1587\">\n");
		xmlEsperado.append("    <nome>geladeira</nome>\n");
		xmlEsperado.append("    <preco>1000.0</preco>\n");
		xmlEsperado.append("    <descrição>Geladeira duas portas</descrição>\n");
		xmlEsperado.append("  </produto>\n");
		xmlEsperado.append("  <produto codigo=\"1588\">\n");
		xmlEsperado.append("    <nome>ferro de passar</nome>\n");
		xmlEsperado.append("    <preco>100.0</preco>\n");
		xmlEsperado.append("    <descrição>Ferro com vaporizador</descrição>\n");
		xmlEsperado.append("  </produto>\n");
		xmlEsperado.append("</compra>");

		List<Produto> produtos = new ArrayList<>();
		Compra compra = new Compra(15, produtos);

		produtos.add(geladeiraMock());
		produtos.add(ferroMock());

		XStream xstream = injectXStream();
		//Ignora collections
		xstream.addImplicitCollection(Compra.class, "produtos");

		String xmlFromObject = xstream.toXML(compra);

		assertEquals(xmlEsperado.toString(), xmlFromObject);
	}
	
	@Test
	public void deveSerializarCadaUmDosProdutosDeUmaCompra() {
		StringBuilder xmlEsperado = new StringBuilder();
		xmlEsperado.append("<compra>\n");
		xmlEsperado.append("  <id>15</id>\n");
		xmlEsperado.append("  <produtos>\n");
		xmlEsperado.append("    <produto codigo=\"1587\">\n");
		xmlEsperado.append("      <nome>geladeira</nome>\n");
		xmlEsperado.append("      <preco>1000.0</preco>\n");
		xmlEsperado.append("      <descrição>Geladeira duas portas</descrição>\n");
		xmlEsperado.append("    </produto>\n");
		xmlEsperado.append("    <produto codigo=\"1588\">\n");
		xmlEsperado.append("      <nome>ferro de passar</nome>\n");
		xmlEsperado.append("      <preco>100.0</preco>\n");
		xmlEsperado.append("      <descrição>Ferro com vaporizador</descrição>\n");
		xmlEsperado.append("    </produto>\n");
		xmlEsperado.append("  </produtos>\n");
		xmlEsperado.append("</compra>");

		List<Produto> produtos = new ArrayList<>();
		Compra compra = new Compra(15, produtos);

		produtos.add(geladeiraMock());
		produtos.add(ferroMock());

		XStream xstream = injectXStream();

		String xmlFromObject = xstream.toXML(compra);

		assertEquals(xmlEsperado.toString(), xmlFromObject);

	}

}
