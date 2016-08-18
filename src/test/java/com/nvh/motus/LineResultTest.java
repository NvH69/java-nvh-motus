package com.nvh.motus;

import com.nvh.motus.model.Motus;
import com.nvh.motus.service.Dictionaries;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LineResultTest {

    private Motus test;

    @Before
    public void setup() {

        new Dictionaries();
        test = new Motus(6, false);
    }

    @Test
    public void shouldMatchCorrectLineResult() {
        //valide le bon fonctionnement de la réponse avec duplication de lettre mal placée
        test.getCurrentTurn().setWordToFind("PATRON");
        List<Integer> found = test.getCurrentTurn().lineResult("PRETER");
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        expected.add(0);
        expected.add(1);
        expected.add(0);
        expected.add(0);
        assertEquals(found, expected);

        //valide le bon fonctionnement de la réponse (cas particulier de bug constaté)
        test.getCurrentTurn().setWordToFind("ASESTE");
        found = test.getCurrentTurn().lineResult("AEREES");
        expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        expected.add(0);
        expected.add(1);
        expected.add(0);
        expected.add(1);
        assertEquals(found, expected);
    }
}
