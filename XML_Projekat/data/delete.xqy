xquery version "1.0-ml";
declare namespace sk = "http://www.ftn.uns.ac.rs/skupstina";

xdmp:node-delete(doc("data/xml/zakon_o_izvrsenju_i_obezbedjenju.xml")//sk:Clan[@Brojcana_oznaka=41])