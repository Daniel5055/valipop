package uk.ac.standrews.cs.valipop.implementations.minimaSearch;

import java.security.InvalidParameterException;
import java.util.Arrays;

/**
 * Specifies the controlling factor of the minima search.
 * 
 * <br>
 * 
 * Either BF (birth factor).
 * 
 * <br>
 * 
 * Or DF (death factor).
 * 
 * @author Tom Dalton (tsd4@st-andrews.ac.uk)
 */
public enum Control {

    BF,
    DF;

    public static Control resolve(String s) {

        s = s.toLowerCase();

        switch (s) {

            case "bf":
                return BF;
            case "df":
                return DF;

        }

        throw new InvalidParameterException("Given Control option is not recognised. Supported options are: " + Arrays.asList(Control.values()));
    }
}
