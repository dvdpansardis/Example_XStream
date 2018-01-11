package br.com.alura.xstream;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.SingleValueConverter;

public class PrecoSimplesConverter implements SingleValueConverter {

	@Override
	public Object fromString(String value) {
		try {
			return getFormatter().parse(value);
		} catch (ParseException e) {
			throw new ConversionException("Nao consegui converter " + value);
		}
	}

	@Override
	public String toString(Object value) {
		return getFormatter().format((Double) value);
	}

	@Override
	public boolean canConvert(Class cls) {
		return cls.isAssignableFrom(Double.class);
	}

	private NumberFormat getFormatter() {
		Locale brasil = new Locale("pt", "br");
		NumberFormat formatter = NumberFormat.getCurrencyInstance(brasil);
		return formatter;
	}
}
