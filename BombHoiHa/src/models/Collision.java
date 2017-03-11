package models;

/**
 * Created by KhoaBeo on 3/12/2017.
 */
public interface Collision {
    GameModel getModel();
    void onContact(Collision other);
}
