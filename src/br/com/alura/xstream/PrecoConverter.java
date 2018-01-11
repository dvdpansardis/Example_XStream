package br.com.alura.xstream;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PrecoConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Double.class);
	}

	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
		Double valor = (Double) obj;
		NumberFormat formatter = getFormatter();
		String valorEmString = formatter.format(valor);
		System.out.println(valorEmString);
		writer.setValue(valorEmString);
	}

	private NumberFormat getFormatter() {
		Locale locale = new Locale("pt", "br");
		NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
		return formatter;
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String value = reader.getValue();
		NumberFormat formatter = getFormatter();
		
		try {
			return formatter.parse(value);
		} catch (ParseException e) {
			throw new ConversionException("Nao consegui converter " + value);
		}
	}

}
