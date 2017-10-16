package cat.foixench.ceina.sqlite.db;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import cat.foixench.ceina.sqlite.data.Shop;

import static org.junit.Assert.*;

/**
 * Created by llorenc on 13/10/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DBHelperTest {

    DBHelper dbHelper;

    @Before
    public void setUp () {
        dbHelper = new DBHelper(InstrumentationRegistry.getTargetContext());
    }


    @Test
    public void getAllShops() throws Exception {

        List<Shop> shopList = dbHelper.getAllShops();

        assertNotNull(shopList);
        assertTrue (shopList.size() > 0);

        Log.d(DBHelper.DATABASE_NAME, "Numero de tiendas : " + shopList.size());
    }

    @Test
    public void getShop() throws Exception {

        Shop shop = dbHelper.getShop(1);
        assertNotNull(shop);

        Log.d (DBHelper.DATABASE_NAME, "[" + shop.getId() + "][" + shop.getName() + "][" + shop.getAddress() + "]");
    }

    @Test
    public void addShop () throws Exception {

        // create a new shop
        Shop shop = new Shop (0, "El racó de la Maria", "Carrer Sants 24, 08014 Barcelona");

        long newId = dbHelper.addShop(shop);
        assertNotEquals( -1, newId);
        Log.d(DBHelper.DATABASE_NAME, "Id nueva tienda " + newId);
        shop.setId(newId);

    }

    @Test
    public void updateShop () throws Exception {
        // update shop number 2
        Shop shop = new Shop (2, "El rebost de la Maria", "Carrer de Sants 28, 08014 Barcelona");
        long rowsUpdates = dbHelper.updateShop(shop);
        assertEquals(1, rowsUpdates);

        Shop newShop = dbHelper.getShop(shop.getId());
        assertEquals(shop.getName(), newShop.getName());
        assertEquals(shop.getAddress(), newShop.getAddress());

        Log.d (DBHelper.DATABASE_NAME, "[" + newShop.getId() + "][" + newShop.getName() + "][" + newShop.getAddress() + "]");
    }

    @Test
    public void deleteShop () throws Exception {
        // delete shop number 2
        Shop shop = new Shop (2, "El rebost de la Maria", "Carrer de Sants 28, 08014 Barcelona");
        int rowsDeleted = dbHelper.deleteShop(shop);
        assertEquals (1, rowsDeleted);
    }

    @Test
    public void dbCicle () throws Exception {
        // crear una nueva tienda
        Shop newShop = new Shop (0, "La botigueta", "Passeig Verdaguer 102, 08700 Igualada");

        long newId = dbHelper.addShop(newShop);

        assertNotEquals(-1, newId);
        newShop.setId(newId);

        // update shop
        newShop.setAddress("Passeig Verdaguer 12, 08700 Igualada");
        long rowsUpdated = dbHelper.updateShop(newShop);
        assertEquals (1, rowsUpdated);

        // recover updated shop
        Shop shop = dbHelper.getShop(newShop.getId());

        assertEquals (shop.getId(), newShop.getId());
        assertEquals (shop.getAddress(), newShop.getAddress());
        assertEquals (shop.getName(), newShop.getName());

        // delete shop
        int rowsDeleted = dbHelper.deleteShop(newShop);

        assertEquals(1, rowsDeleted);

        // recover shop
        shop = dbHelper.getShop(newShop.getId());
        assertNull(shop);


    }
}