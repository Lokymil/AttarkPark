package fr.esiea.mobile.attrackpark.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.util.Log;

import fr.esiea.mobile.attrackpark.database.ParkContentProvider;

/**
 * Created by Christophe on 20/11/2015.
 */
public class ParkContentProviderTest extends ProviderTestCase2<ParkContentProvider> {

    private ContentResolver contentResolver;

    public ParkContentProviderTest() {
        super(ParkContentProvider.class, "fr.esiea.mobile.attrackpark");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        contentResolver = getMockContentResolver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        cleanDataBase();
    }

    private void cleanDataBase(){
        contentResolver.delete(ParkContentProvider.CONTENT_URI, null, null);
    }

    public void testInsert() {
        // Dat method name <3 !!
        Uri myRowUri = contentResolver.insert(ParkContentProvider.CONTENT_URI,getDatValue());
        Log.i("Test", "Row URI " + myRowUri.toString());
        assertNotNull(myRowUri);

        Cursor cursor = contentResolver.query(ParkContentProvider.CONTENT_URI, null, null, null, null);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        Log.i("Test", "cursor after query " + cursor.getColumnCount());

        for (int i = 0 ; i<cursor.getColumnCount() ; i++) {
            Log.i("Test",cursor.getColumnName(i));
            Log.i("Test",""+cursor.getDouble(cursor.getColumnIndex("_id")));
        }

        Cursor cursor2 = contentResolver.query(myRowUri, null , null, null, null);
        assertNotNull(cursor2);
        assertEquals(1, cursor2.getCount());
    }

    public void testQuery(){
        Cursor cursor = contentResolver.query(ParkContentProvider.CONTENT_URI, null, null, null, null);
        assertNotNull(cursor);
    }

    // Dat method name *.* !
    private ContentValues getDatValue() {
        ContentValues newValues = new ContentValues();
        newValues.put(ParkContentProvider.KEY_NAME, "Name");
        newValues.put(ParkContentProvider.KEY_DESCRIPTION, "Descritption");
        newValues.put(ParkContentProvider.KEY_URL, "www.google.com");
        newValues.put(ParkContentProvider.KEY_PAYS, "pays");
        newValues.put(ParkContentProvider.KEY_LATITUDE, 0);
        newValues.put(ParkContentProvider.KEY_LONGITUDE, 0);
        newValues.put(ParkContentProvider.KEY_IMG_URL, "https://lh4.ggpht.com/wKrDLLmmxjfRG2-E-k5L5BUuHWpCOe4lWRF7oVs1Gzdn5e5yvr8fj-ORTlBF43U47yI=w300");

        return newValues;
    }
}
