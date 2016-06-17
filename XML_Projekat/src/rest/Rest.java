package rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import daobeans.implementation.AktDaoLocal;
import daobeans.implementation.AmandmanDaoLocal;
import daobeans.implementation.KorisnikDaoLocal;
import model.akt.Akt;
import model.akt.Clan;
import model.amandman.Amandman;
import model.korisnik.Korisnici;
import model.korisnik.Korisnik;
import util.CollectionConstants;
import util.SearchResultsUtil;
import util.StringConstants;
import validation.XMLValidator;

@Stateless
@Path("/services")
public class Rest {

	@EJB
	DataTest data;

	@EJB
	AktDaoLocal aktDao;
	
	@EJB
	AmandmanDaoLocal amandmanDao;
	
	@EJB
	KorisnikDaoLocal korisnikDao;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}

	@GET
	@Path("/login/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik loginUser(@PathParam("username") String username, @PathParam("password") String password) {

		Korisnici korisnici = korisnikDao.getUsers();
		Korisnik retVal = null;
		for(Korisnik k:korisnici.getKorisnik())
		{
			if(k.getKorisnickoIme().equals(username) && k.getLozinka().equals(password))
				retVal = k;
		}
		
		return retVal;
		
	}

	@POST
	@Path("/search/akt/keyword/{keyword}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> pretragaAktaPoKljucnojReci(@PathParam("keyword")String keyword)
	{
		List<Object> retVal = null;
		try {
			retVal = new ArrayList<Object>(aktDao.findByKeyWord(keyword));
		} catch (IOException e) {
			System.out.println("Greska prilikom pretrage akta po kljucnoj reci");
		}
		return retVal;
	}
	
	@GET
	@Path("/search/akt/meta")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> pretragaAktaPoMetaPodacima(@QueryParam("dateFrom")String dateFrom, @QueryParam("dateTo")String dateTo) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		//System.out.println(sdf.format(sdf.parse(dateFrom)) + " " + sdf.format(sdf.parse(dateTo)));
		try {
			return aktDao.findByMetaData(sdf.format(sdf.parse(dateFrom)), sdf.format(sdf.parse(dateTo)));
		} catch (IOException e) {
			System.out.println("Greska prilikom pretrage akta sa metapodacima");
			return null;
		}
	}
	
	@GET
	@Path("/akt/html/{uri}")
	@Produces(MediaType.TEXT_HTML)
	public String getHTMLAkt(@PathParam("uri")String uri) {
		System.out.println(uri);
		try {
			return (String)aktDao.findById(uri, "akt");
		} catch (JAXBException | IOException e) {
			System.out.println("Greska prilikom prikaza akt html-a");
			return null;
		} 
	}
	
	@GET
	@Path("/akt/pdf")
	@Produces("application/pdf")
	public byte[] getPDFAkt() {
		FileInputStream fis;
		BufferedInputStream inputStream;
		byte[] fileBytes = null;
		try {
			File file = new File("D:/FTN/Semestar_8/XML/Projekat/XML/XML_Projekat/WebContent/gen/pdf/zakon_o_izvrsenju_i_obezbedjenju.pdf"); 
			fis = new FileInputStream(file);
			inputStream = new BufferedInputStream(fis);
			fileBytes = new byte[(int) file.length()];
	        inputStream.read(fileBytes);
	        inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
        return fileBytes;
	}
	
	@GET
	@Path("/amandman/html")
	@Produces(MediaType.TEXT_HTML)
	public String getHTMLAmandman(@QueryParam("uri")String uri) {
		try {
			return (String)aktDao.findById(uri, "amandman");
		} catch (JAXBException | IOException e) {
			System.out.println("Greska prilikom prikaza amandmana html-a");
			return null;
		} 
	}
	
	@GET
	@Path("/predlozeniAmandmani")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> predlozeniAmandmani()
	{
		try {
			ArrayList<Object> retVal = new ArrayList<Object>(aktDao.findAllAmandmani());
			Predicate<Object> predlozen = new Predicate<Object>() {
				  @Override
				  public boolean test(Object s) {
				    return !((SearchResultsUtil)s).getDocumentCollection().equals("AMANDMAN U PROCEDURI");
				  }
				};
			retVal.removeIf(predlozen);
			return retVal;
		} catch (IOException e) {
			System.out.println("Greska prilikom getovanja amandmana");
			return null;
		}
	}
	
	@POST
	@Path("/vote/{za}/{uzdrzano}/{protiv}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> vote(@PathParam("za") String za, @PathParam("uzdrzano") String uzdrzano,
			@PathParam("protiv") String protiv, String amandman) {
		try {
			Integer z = Integer.parseInt(za);
			Integer u = Integer.parseInt(uzdrzano);
			Integer p = Integer.parseInt(protiv);
			
			if ( z > u + p){
				amandmanDao.merge(amandman);
			}else
			{
				amandmanDao.changeCollection(amandman, new String[]{CollectionConstants.amandmani,CollectionConstants.amandmanPrihvacen});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getNasloviPredlozenihAmandmana();
	}
	
	@GET
	@Path("/akti")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> getAkti(){
		try {
			return aktDao.findAll();
		} catch (IOException | JAXBException e) {
			System.out.println("Greska prilikom dobavljanja svih akata");
			return null;
		}
	}
	
	@POST
	@Path("/akt/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean dodajNoviAkt(String document) throws JAXBException, IOException
	{
		Object retVal;
		if((retVal = XMLValidator.validateXML("akt", document)) == null)
			System.out.println("Nije validan xml");
		else
		{
			try {
				aktDao.persist(((Akt)retVal), StringConstants.formatName(((Akt)retVal).getNaslov()));
			} catch (JAXBException | IOException e) {
				System.out.println("Greska prilikom dodavanja novog akta");
				return false;
			}
		}
		return true;
	}
	
	@GET
	@Path("/akt/prihvati/{aktId}")
	public boolean prihvatiAkt(@PathParam("aktId")String aktId) {
		try {
			aktDao.changeCollection(aktId, new String[] {CollectionConstants.akti, CollectionConstants.aktPrihvacen});
		} catch (IOException e) {
			System.out.println("Greska prilikom prihvatanja akta");
			return false;
		}
		return true;
	}
	
	@GET
	@Path("/akt/{naslov}")
	@Produces(MediaType.APPLICATION_JSON)
	public Clan getClanByNaslovAkta(@PathParam("naslov") String naslov){
		List<Akt> akti = data.getAkti();
		for (Akt akt : akti) {
			if (akt.getNaslov().equals(naslov)){
				return akt.getClan().get(0);
			}
		}
		
		return null;
	}
	
	@GET
	@Path("/akt/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> filterAkta(@PathParam("filter") String filter){
		System.out.println("Filtriram po: " + filter);
		return null;
	}

	@GET
	@Path("/amandmani")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> getAmandmani(){
		try {
			return aktDao.findAllAmandmani();
		} catch (IOException e) {
			System.out.println("Greska prilikom getovanja amandmana");
			return null;
		}
	}
	
	@GET
	@Path("/predlozeniAmandmani")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getNasloviPredlozenihAmandmana(){
		List<Amandman> amandman = data.getPredlozeniAmandmani();
		List<String> naslovi = new ArrayList<String>();
		for (Amandman a : amandman) {
			naslovi.add(a.getOperacija());
		}
		return naslovi;
	}
	
	@GET
	@Path("/amandman/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> filterAmandmana(@PathParam("filter") String filter){
		System.out.println("Filtriram po: " + filter);
		return null;
	}
	
	@GET
	@Path("/predlozeniAmandman/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> filterPredlozenihAmandmana(@PathParam("filter") String filter){
		System.out.println("Filtriram po: " + filter);
		return getNasloviPredlozenihAmandmana();
	}
	
	@GET
	@Path("/odabraniAkti")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getNasloviOdabranihAkata(){
		List<Akt> akti = data.getOdabraniAkti();
		List<String> naslovi = new ArrayList<String>();
		for (Akt akt : akti) {
			naslovi.add(akt.getNaslov());
		}
		return naslovi;
	}
	
	@PUT
	@Path("/odabraniAkti/add/{naslov}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> dodajUOdabranjeAkte(@PathParam("naslov") String naslov){
		List<Akt> akti = data.getAkti();
		for (Akt akt : akti) {
			if (akt.getNaslov().equals(naslov)){
				data.getOdabraniAkti().add(akt);
			}
		}
		return getNasloviOdabranihAkata();
	}
	
	@DELETE
	@Path("/odabraniAkti/remove/{naslov}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> obrisiOdabraniAkt(@PathParam("naslov") String naslov){
		List<Akt> akti = data.getAkti();
		for (Akt akt : akti) {
			if (akt.getNaslov().equals(naslov)){
				data.getOdabraniAkti().remove(akt);
				break;
			}
		}
		return getNasloviOdabranihAkata();
	}
	
	@POST
	@Path("/amandman/add")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean dodajAmandman(String document){
		System.out.println(document);
		Object retVal;
		if((retVal = XMLValidator.validateXML("amandman", document)) == null)
			System.out.println("Nije validan xml");
		else
		{
			try {
				amandmanDao.persist(((Amandman)retVal), ((Amandman)retVal).getKontekst().getReferentniZakon()+"/"+((Amandman)retVal).getNaziv());
			} catch (JAXBException | IOException e) {
				System.out.println("Greska prilikom dodavanja amandmana.");
				return false;
			}
		}
		return true;
	}
	
	@PUT
	@Path("/amandman/updateAkt/{amandmanId}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean primeniAmandmanNaAkt(@PathParam("amandmanId")String amandmanId){
		try {
			amandmanDao.merge(amandmanId);
		} catch (IOException | JAXBException e) {
			System.out.println("Greska prilikom primene amandmana na akt");
			return false;
		}
		return true;
	}
	
	@POST
	@Path("/remove")
	public boolean removeAmandman(String uri)
	{
		try {
			amandmanDao.remove(uri);
		} catch (IOException e) {
			System.out.println("Greska prilikom brisanja akta ili amandmana");
			return false;
		}
		return true;
	}
}
