package com.example.Prepareo_Owl_to_Mongo;
import java.io.File;
import java.io.FileOutputStream;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Owl_To_JSON
{
    public static void main(String[] args) throws Exception
    {
        File file = new File("/C:/Users/c/IdeaProjects/Prepareo/Resources/Learning Journey Ontology-PY20181011.owl");
     //   OutputStream outputstream=null;
        OWLDocumentFormat ontologyFormat = new RDFJsonLDDocumentFormat();
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
     //   System.out.println("Axioms: " +ontology.getAxiomCount()+ ", Format: "
     //           + manager.getOntologyFormat(ontology) );
        File newF = new File("/C:/Users/c/IdeaProjects/Prepareo/Resources/Convert.json-ld");
        FileOutputStream fop = new FileOutputStream(newF);
   // manager.saveOntology(ontology, ontologyFormat, outputstream);
        // Saves
        manager.saveOntology( ontology, ontologyFormat, fop);
        fop.close();
    }
}