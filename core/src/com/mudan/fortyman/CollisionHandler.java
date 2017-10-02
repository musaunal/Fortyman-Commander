package com.mudan.fortyman;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mudan.fortyman.Elemanlar.AskerKalıp;
import com.mudan.fortyman.Elemanlar.MapElemanı.DusmanOrdu;
import com.mudan.fortyman.Elemanlar.MapElemanı.Ordu;
import com.mudan.fortyman.Elemanlar.Piyade;

/**
 * Created by musa on 29.09.2017.
 */

public class CollisionHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();   // collisionda iki nesne var temas eden ve edilen bir tanesi fixA diğeri fixB
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Fortyman.PIYADE | Fortyman.NESNE:
                if(fixA.getFilterData().categoryBits == Fortyman.PIYADE)
                    ((Piyade) fixA.getUserData()).setState(AskerKalıp.Vaziyet.KOS);
                else
                    ((Piyade) fixB.getUserData()).setState(AskerKalıp.Vaziyet.KOS);
                break;
            case Fortyman.KUTU_ORDU | Fortyman.KUTU_DUSMAN:
                if(fixA.getFilterData().categoryBits == Fortyman.KUTU_DUSMAN)
                    ((Ordu) fixB.getUserData()).savasBaslat((DusmanOrdu)fixA.getUserData() );
                else
                    ((Ordu) fixA.getUserData()).savasBaslat((DusmanOrdu)fixB.getUserData());
                break;
            case Fortyman.PIYADE | Fortyman.DUSMAN_PIYADE:
                if(fixA.getFilterData().categoryBits == Fortyman.PIYADE)
                    ((Piyade) fixA.getUserData()).setState(AskerKalıp.Vaziyet.HUCUM);
                else
                    ((Piyade) fixB.getUserData()).setState(AskerKalıp.Vaziyet.HUCUM);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
