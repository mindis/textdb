package edu.uci.ics.textdb.textql.statements.predicates;

import org.junit.Assert;
import org.junit.Test;

import edu.uci.ics.textdb.api.common.Schema;
import edu.uci.ics.textdb.common.constants.SchemaConstants;
import edu.uci.ics.textdb.textql.planbuilder.beans.PassThroughBean;
import edu.uci.ics.textdb.textql.statements.StatementTestUtils;
import edu.uci.ics.textdb.web.request.beans.OperatorBean;

/**
 * This class contains test cases for the SelectAllFieldsPredicate class.
 * The generateOperatorBean method is tested.
 * There are no constructors, getters nor setters to test. 
 * 
 * @author Flavio Bayer
 *
 */
public class SelectAllFieldsPredicateTest {

    /**
     * Test the generateOperatorBean method.
     * Build a SelectAllFieldsPredicate, invoke the generateOperatorBean and check
     * whether a PassThroughBean with the right attributes is returned.
     */
    @Test
    public void testGenerateOperatorBean() {
        SelectAllFieldsPredicate selectAllFieldsPredicate = new SelectAllFieldsPredicate();
        OperatorBean projectionBean;
        String operatorId;
        
        operatorId = "xxx";
        projectionBean = selectAllFieldsPredicate.generateOperatorBean(operatorId);
        Assert.assertEquals(projectionBean, new PassThroughBean(operatorId, "PassThrough"));

        operatorId = "y0a9";
        projectionBean = selectAllFieldsPredicate.generateOperatorBean(operatorId);
        Assert.assertEquals(projectionBean, new PassThroughBean(operatorId, "PassThrough"));
    }

    /**
     * Test the generateOutputSchema method.
     * This test use an empty Schema as input.
     * The expected output Schema is the same as the input Schema.
     */
    @Test
    public void testGenerateOutputSchema00() {
        SelectAllFieldsPredicate selectAllFieldsPredicate = new SelectAllFieldsPredicate();
        
        Schema inputSchema = new Schema();
        
        Schema generatedOutputSchema = selectAllFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = inputSchema;
        
        Assert.assertEquals(expectedOutputSchema, generatedOutputSchema);
    }
    
    /**
     * Test the generateOutputSchema method.
     * This test uses a Schema with attributes with all FieldTypes.
     * The expected output Schema is the same as the input Schema.
     */
    @Test
    public void testGenerateOutputSchema01() {
        SelectAllFieldsPredicate selectAllFieldsPredicate = new SelectAllFieldsPredicate();
        
        Schema inputSchema = StatementTestUtils.ALL_FIELD_TYPES_SCHEMA;
        
        Schema generatedOutputSchema = selectAllFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = inputSchema;
        
        Assert.assertEquals(expectedOutputSchema, generatedOutputSchema);
    }
    
    /**
     * Test the generateOutputSchema method.
     * This test uses a Schema with all the attributes in SchemaConstants.
     * The expected output Schema is the same as the input Schema.
     */
    @Test
    public void testGenerateOutputSchema02() {
        SelectAllFieldsPredicate selectAllFieldsPredicate = new SelectAllFieldsPredicate();
        
        Schema inputSchema = new Schema(
                SchemaConstants._ID_ATTRIBUTE,
                SchemaConstants.PAYLOAD_ATTRIBUTE,
                SchemaConstants.SPAN_LIST_ATTRIBUTE
            );
        
        Schema generatedOutputSchema = selectAllFieldsPredicate.generateOutputSchema(inputSchema);
        Schema expectedOutputSchema = inputSchema;
        
        Assert.assertEquals(expectedOutputSchema, generatedOutputSchema);
    }
    
}
