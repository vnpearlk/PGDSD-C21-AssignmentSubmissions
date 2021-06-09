package upgrad;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class CRUDHelper {

    /**
     * Display ALl products
     *
     * @param collection
     */
    public static void displayAllProducts(MongoCollection<Document> collection) {
        System.out.println("------ Displaying All Products ------");

        // Build Query to find all documents
        // add (optionally) projection to include only the common fields & get MongoCursor
        // Iterate cursor & call respective PrintHelper method to display output as required.
        // Additionally usage of Projections here seems to be optional, as the PrintHelper.printSingleCommonAttributes()
        // method is hardcoded to print the common attributes only, irrespective of whether other
        // non-common / non-null columns are present or not part of the fetched result mongo cursor.
        MongoCursor<Document> mongoCursor = collection
                .find()
                .projection(fields(include("ProductId", "Title", "Manufacturer", "Category")))
                .cursor();

        // Call printSingleCommonAttributes to display the attributes on the Screen
        while (mongoCursor.hasNext()) {
            PrintHelper.printSingleCommonAttributes(mongoCursor.next());
        }
    }

    /**
     * Display top 5 Mobiles
     *
     * @param collection
     */
    public static void displayTop5Mobiles(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Top 5 Mobiles ------");

        // Build Query to find all documents with category as "Mobile", Limit records to 5 &
        // get MongoCursor on returned documents.
        // Iterate cursor & call respective PrintHelper method to display output as required.
        MongoCursor<Document> mongoCursor = collection
                .find(new Document("Category", "Mobile"))
                .limit(5)
                .cursor();

        // Call printAllAttributes to display the attributes on the Screen
        while (mongoCursor.hasNext()) {
            PrintHelper.printAllAttributes(mongoCursor.next());
        }
    }

    /**
     * Display products ordered by their categories in Descending order without auto generated Id
     *
     * @param collection
     */
    public static void displayCategoryOrderedProductsDescending(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Products ordered by categories ------");

        // Build Query to find all documents, Use Projections to exclude ID field &
        // sort documents by $Category field in descending order & get MongoCursor on returned documents.
        // Iterate cursor & call respective PrintHelper method to display output as required.
        MongoCursor<Document> mongoCursor = collection
                .find()
                .projection(fields(excludeId()))
                .sort(descending("Category"))
                .cursor();

        // Call printAllAttributes to display the attributes on the Screen
        while (mongoCursor.hasNext()) {
            PrintHelper.printAllAttributes(mongoCursor.next());
        }
    }


    /**
     * Display number of products in each group
     *
     * @param collection
     */
    public static void displayProductCountByCategory(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Product Count by categories ------");

        // Build Query to aggregate documents, by grouping documents by "$Category" field &
        // Accumulate the number of records using Accumulator.sum to keep adding 1 for
        // every record grouped by "$Category" field,
        // sort the list & get MongoCursor on returned documents.
        // Iterate cursor & call respective PrintHelper method to display output as required.
        MongoCursor<Document> mongoCursor = collection
                .aggregate(Arrays.asList(
                        Aggregates.group("$Category", Accumulators.sum("Count", 1))
                        )
                )
                .cursor();

        // Call printProductCountInCategory to display the attributes on the Screen
        while (mongoCursor.hasNext()) {
            PrintHelper.printProductCountInCategory(mongoCursor.next());
        }
    }

    /**
     * Display Wired Headphones
     *
     * @param collection
     */
    public static void displayWiredHeadphones(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Wired headphones ------");

        // Build query to find all documents matching criteria set by BasicDBObject &
        // get MongoCursor on returned documents.
        // Iterate cursor & call respective PrintHelper method to display output as required.
        BasicDBObject criteria = new BasicDBObject();
        criteria.append("Category", "Headphone");
        criteria.append("ConnectorType", "Wired");

        MongoCursor<Document> mongoCursor = collection
                .find(criteria)
                .cursor();

        // Call printAllAttributes to display the attributes on the Screen
        while (mongoCursor.hasNext()) {
            PrintHelper.printAllAttributes(mongoCursor.next());
        }
    }
}