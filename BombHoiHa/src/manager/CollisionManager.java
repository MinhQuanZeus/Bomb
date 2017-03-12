package manager;

import controllers.GameController;
import controllers.ItemController;
import models.Collision;
import models.GameModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by KhoaBeo on 3/12/2017.
 */
public class CollisionManager {

    private List<Collision> collisionList;

    public CollisionManager() {
        collisionList = new ArrayList<>();
    }

    public void add(Collision collision) {
        collisionList.add(collision);
    }

    public void remove(Collision collision) {
        collisionList.remove(collision);
    }

    public void run() {
        remove();
        for (int i = 0; i < collisionList.size() - 1; i++) {
            Collision collisionI = collisionList.get(i);
            GameModel modelI = collisionI.getModel();
            for (int j = i + 1; j < collisionList.size(); j++) {
                Collision collisionJ = collisionList.get(j);
                GameModel modelJ = collisionJ.getModel();

                if (modelI.overlap(modelJ)) {
                    collisionJ.onContact(collisionI);
                    collisionI.onContact(collisionJ);
                }
            }
        }
    }

    private void remove(){
        Iterator<Collision> gameControllerIterator = collisionList.iterator();
        while (gameControllerIterator.hasNext()){
            if(!gameControllerIterator.next().getModel().isAlive())
                gameControllerIterator.remove();
        }
    }
}
