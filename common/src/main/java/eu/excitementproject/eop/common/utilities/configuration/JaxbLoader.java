package eu.excitementproject.eop.common.utilities.configuration;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;



/**
 * Generic Jaxb loader. The {@code load} method marshals the input xml file according to the parameters in the given package of JaxB types, and 
 * returns the xml as a C "configuration type". The package, including C, is supposed to be generated with your application's XSD file.
 * <p>
 *
 * <b>Disclaimer:</b> This loader won't work with the products of such XSDs as the one that {@code ConfigurationFile} uses, because 
 * in that case the Unmarshaller returns a {@code JAXBElement<C>}, while here a C return type is assumed. 
 * <br>
 * See:
 * <br> 
 * <tr>{@code return ((JAXBElement<ConfigurationType>) unmarshaller.unmarshal(iConfigurationXmlFile)).getValue();}
 * <br>
 * vs.
 * <br>
 * {@code return (C) unmarshaller.unmarshal(file);}
 * <br>
 * We currently don't know what in the XSD makes this difference.
 *   
 * @param <C> The configuration type class generated by your application's XSD file
 * 
 * @author Shachar Mirkin 
 * @since 2010
 */
public class JaxbLoader<C> 
{
	private Unmarshaller unmarshaller;

	/**
	 * Constructor - set the type of jaxb files
	 * 
	 * @param packageName
	 *           the package in which the Jaxb files are located
	 * @throws JAXBException if got empty packageName
	 */
	public JaxbLoader(String packageName) throws JAXBException 
	{
		if (packageName == null)
			throw new JAXBException("Got empty packageName");
		
		JAXBContext jc = JAXBContext.newInstance(packageName);
		unmarshaller = jc.createUnmarshaller();
	}

	/**
	 * Read the file and load it into a ConfigurationType object
	 * 
	 * @param file
	 *            a file in the format that's compatible with the jaxb package
	 *            files
	 * @return An ConfigurationType object (supposed to be generated by your application's XSD file)
	 * 			
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public C load(File file) throws JAXBException 
	{
		if (file == null)
			throw new JAXBException("Got empty file");
		if (!file.exists())
			throw new JAXBException(file + " doesn't exist");
		if (file.isDirectory())
			throw new JAXBException(file + " is a directory");	
		
		try {	return (C) unmarshaller.unmarshal(file);	}
		catch (Exception e) {	throw new JAXBException("malformed file " + file, e);	}
	}
}