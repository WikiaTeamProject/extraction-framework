package org.dbpedia.extraction.mappings.rml.model.resource

import org.apache.jena.rdf.model.Resource
import org.dbpedia.extraction.mappings.rml.model.voc.Property
import org.dbpedia.extraction.ontology.RdfNamespace

/**
  * Represents an object map
  */
class RMLObjectMap(override val resource: Resource) extends RMLResource(resource) {

  lazy val parentTriplesMap : RMLTriplesMap = getParentTriplesMap
  lazy val reference : RMLLiteral = getReference

  def addRMLReference(literal: RMLLiteral) =
  {
    resource.addLiteral(createProperty(RdfNamespace.RML.namespace + "reference"), literal.toString())
  }

  def addParentTriplesMap(uri: RMLUri) : RMLTriplesMap =
  {
    val parentTriplesMap = factory.createRMLTriplesMap(uri)
    resource.addProperty(createProperty(RdfNamespace.RR.namespace + "parentTriplesMap"), parentTriplesMap.resource)
    parentTriplesMap
  }

  def addConstant(literal: RMLLiteral) : RMLObjectMap =
  {
    resource.addLiteral(createProperty(RdfNamespace.RR.namespace + "constant"), literal.toString())
    this
  }

  def addConstant(uri: RMLUri) : RMLObjectMap =
  {
    resource.addProperty(createProperty(RdfNamespace.RR.namespace + "constant"), createProperty(uri.toString))
    this
  }

  def addIRITermType() : RMLObjectMap =
  {
    resource.addProperty(createProperty(RdfNamespace.RR.namespace + "termType"), createProperty(RdfNamespace.RR.namespace + "IRI"))
    this
  }

  def addLiteralTermType() : RMLObjectMap =
  {
    resource.addProperty(createProperty(RdfNamespace.RR.namespace + "termType"), createProperty(RdfNamespace.RR.namespace + "Literal"))
    this
  }

  def addDatatype(uri : RMLUri) = {
    resource.addProperty(createProperty(RdfNamespace.RR.namespace + "datatype"), createProperty(uri.toString))
    this
  }

  def addLanguage(language : String) = {
    resource.addProperty(createProperty(RdfNamespace.RR.namespace + "language"), language)
  }

  def hasReference : Boolean = {
    reference != null
  }

  private def getParentTriplesMap : RMLTriplesMap = {
    val property = resource.listProperties(createProperty(Property.PARENTTRIPLESMAP))
    if(property.hasNext) {
      val stmnt = property.nextStatement()
      val parentTriplesMapResource = stmnt.getObject.asResource()
      RMLTriplesMap(parentTriplesMapResource)
    } else null
  }

  private def getReference : RMLLiteral = {
    val property = resource.listProperties(createProperty(Property.REFERENCE))
    if(property.hasNext) {
      val stmnt = property.nextStatement()
      val referenceString = stmnt.getObject.asLiteral().toString
      val reference = RMLLiteral(referenceString)
      reference
    } else null
  }

}

object RMLObjectMap {

  /**
    * Creates an RMLObjectMap straight from a given resource
    *
    * @param resource
    * @return
    */
  def apply(resource: Resource) : RMLObjectMap = {
    new RMLObjectMap(resource)
  }




}