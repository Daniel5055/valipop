package uk.ac.standrews.cs.valipop.simulationEntities;

import uk.ac.standrews.cs.valipop.statistics.analysis.validation.contingencyTables.TreeStructure.SexOption;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Utility functions to navigate through population model via persons.
 */
public class PopulationNavigation {

    private static final int NUMBER_OF_GENERATIONS_TO_EXCLUDE = 3;

    public static Collection<IPerson> siblingsOf(IPerson person) {

        // Include half-siblings.

        final Collection<IPerson> siblings = new TreeSet<>();
        final IPartnership parents = person.getParents();

        if (parents != null) {

            siblings.addAll(getAllChildren(parents.getMalePartner()));
            siblings.addAll(getAllChildren(parents.getFemalePartner()));
        }

        return siblings;
    }

    public static Collection<IPerson> imidiateFamilyOf(IPerson root) {

        Collection<IPerson> family = new ArrayList<>();

        family.addAll(siblingsOf(root));
        family.addAll(siblingsOf(root));
        family.addAll(getAllChildren(root));
        family.addAll(ancestorsOf(root, 1));

        return family;

    }

    private static Collection<IPerson> siblingsOf(IPerson person, SexOption sex) {

        Collection<IPerson> siblings = siblingsOf(person);
        siblings.removeIf(p -> p.getSex() != sex);
        return siblings;
    }

    public static Collection<IPerson> sistersOf(IPerson person) {

        return siblingsOf(person, SexOption.FEMALE);
    }

    public static Collection<IPerson> brothersOf(IPerson person) {

        return siblingsOf(person, SexOption.MALE);
    }

    public static Collection<IPerson> partnersOf(IPerson person) {

        Collection<IPerson> partners = new TreeSet<>();

        for (IPartnership partnership : person.getPartnerships()) {
            partners.add(partnership.getPartnerOf(person));
        }
        return partners;
    }

    private static Collection<IPerson> descendantsOf(Collection<IPerson> people) {

        Collection<IPerson> descendants = new TreeSet<>();
        for (IPerson person : people) {
            descendants.addAll(descendantsOf(person));
        }
        return descendants;
    }

    public static Collection<IPerson> descendantsOf(IPerson person) {

        return descendantsOf(person, NUMBER_OF_GENERATIONS_TO_EXCLUDE);
    }

    public static Collection<IPerson> femaleDescendantsOf(Collection<IPerson> people) {

        Collection<IPerson> descendants = descendantsOf(people);
        descendants.removeIf(p -> p.getSex() != SexOption.FEMALE);
        return descendants;
    }

    public static Collection<IPerson> femaleDescendantsOf(IPerson person) {

        return descendantsOf(person, SexOption.FEMALE);
    }

    public static Collection<IPerson> maleDescendantsOf(IPerson person) {

        return descendantsOf(person, SexOption.MALE);
    }

    private static Collection<IPerson> descendantsOf(IPerson person, SexOption sex) {

        Collection<IPerson> descendants = descendantsOf(person);
        descendants.removeIf(p -> p.getSex() != sex);
        return descendants;
    }

    public static Collection<IPerson> ancestorsOf(IPerson person) {

        return ancestorsOf(person, NUMBER_OF_GENERATIONS_TO_EXCLUDE);
    }

    public static Collection<IPerson> femaleAncestorsOf(Collection<IPerson> people) {

        Collection<IPerson> ancestors = new ArrayList<>();
        for (IPerson person : people) {
            ancestors.addAll(femaleAncestorsOf(person));
        }
        return ancestors;
    }

    public static Collection<IPerson> femaleAncestorsOf(IPerson person) {

        return ancestorsOf(person, SexOption.FEMALE);
    }

    public static Collection<IPerson> maleAncestorsOf(IPerson person) {

        return ancestorsOf(person, SexOption.MALE);
    }

    private static Collection<IPerson> ancestorsOf(IPerson person, SexOption sex) {

        Collection<IPerson> ancestors = ancestorsOf(person);
        ancestors.removeIf(p -> p.getSex() != sex);
        return ancestors;
    }

    private static Collection<IPerson> descendantsOf(IPerson person, int generations) {

        Collection<IPerson> descendants = new TreeSet<>();

        if (generations > 0) {

            for (IPerson child : getAllChildren(person)) {

                descendants.add(child);
                descendants.addAll(descendantsOf(child, generations - 1));
            }
        }

        return descendants;
    }

    public static Collection<IPerson> getAllChildren(IPerson person) {

        Collection<IPerson> children = new ArrayList<>();

        for (IPartnership partnership : person.getPartnerships()) {
            children.addAll(partnership.getChildren());
        }

        return children;
    }

    private static Collection<IPerson> ancestorsOf(IPerson person, int generations) {

        Collection<IPerson> ancestors = new TreeSet<>();

        if (generations > 0) {

            IPartnership parentsPartnership = person.getParents();

            if (parentsPartnership != null) {
                IPerson mother = parentsPartnership.getFemalePartner();
                IPerson father = parentsPartnership.getMalePartner();

                ancestors.add(mother);
                ancestors.add(father);

                ancestors.addAll(ancestorsOf(mother, generations - 1));
                ancestors.addAll(ancestorsOf(father, generations - 1));
            }
        }

        return ancestors;
    }

    public static Collection<IPerson> partnersOf(Collection<IPerson> people) {

        List<IPerson> partners = new ArrayList<>();

        for (IPerson person : people) {
            partners.addAll(partnersOf(person));
        }
        return partners;
    }

    public static boolean aliveOnDate(IPerson person, LocalDate date) {

        if (!person.getBirthDate().isAfter(date)) {

            LocalDate deathDate = person.getDeathDate();
            return deathDate == null || date.isBefore(deathDate);
        }
        return false;
    }

    public static IPerson getLastChild(IPerson person) {

        LocalDate latestChildBirthDate = LocalDate.MIN;
        IPerson child = null;

        for (IPartnership p : person.getPartnerships()) {
            for (IPerson c : p.getChildren()) {

                if (!latestChildBirthDate.isAfter(c.getBirthDate())) {
                    latestChildBirthDate = c.getBirthDate();
                    child = c;
                }
            }
        }

        return child;
    }

    public static boolean lastPartnerDied(IPerson person, LocalDate currentDate) {

        try {
            IPerson lastPartner = getLastChild(person).getParents().getPartnerOf(person);
            return !aliveOnDate(lastPartner, currentDate);

        } catch (NullPointerException e) {
            return true;
        }
    }

    public static int ageOnDate(IPerson person, LocalDate currentDate) {

        LocalDate birthDate = person.getBirthDate();

        if (birthDate.getDayOfMonth() == 1 && birthDate.getMonthValue() == 1 && currentDate.getDayOfMonth() == 1 && currentDate.getMonthValue() == 1) {
            int age = Period.between(person.getBirthDate(), currentDate).getYears() - 1;
            return age == -1 ? 0 : age;
        } else {
            return Period.between(person.getBirthDate(), currentDate).getYears();
        }

    }

    public static int numberOfChildrenInLatestPartnership(IPerson person) {

        return getLastChild(person).getParents().getChildren().size();
    }

    public static boolean bornInYear(IPerson person, Year year) {

        LocalDate birthDate = person.getBirthDate();
        return birthDate != null && birthDate.getYear() == year.getValue();
    }

    public static boolean diedInYear(IPerson person, Year year) {

        LocalDate deathDate = person.getDeathDate();
        return deathDate != null && deathDate.getYear() == year.getValue();
    }

    public static boolean diedAfter(IPerson person, LocalDate date) {

        LocalDate deathDate = person.getDeathDate();
        return deathDate == null || deathDate.isAfter(date);
    }

    public static Collection<IPartnership> getPartnershipsActiveInYear(IPerson person, Year year) {

        Collection<IPartnership> activePartnerships = new ArrayList<>();

        for (IPartnership partnership : person.getPartnerships()) {

            LocalDate startDate = partnership.getPartnershipDate();

            if (startDate.getYear() == year.getValue()) {
                activePartnerships.add(partnership);
            } else {
                for (IPerson p : partnership.getChildren()) {
                    if (p.getBirthDate().getYear() == year.getValue()) {
                        activePartnerships.add(partnership);
                        break;
                    }
                }
            }
        }

        return activePartnerships;
    }

    public static IPartnership getLastPartnership(IPerson person) {

        LocalDate latestPartnershipDate = LocalDate.MIN;
        IPartnership partnership = null;

        for (IPartnership p : person.getPartnerships()) {
            if (latestPartnershipDate.isBefore(p.getPartnershipDate())) {
                latestPartnershipDate = p.getPartnershipDate();
                partnership = p;
            }
        }
        return partnership;
    }

    public static IPartnership getLastPartnershipBeforeDate(IPerson person, LocalDate date) {

        LocalDate latestPartnershipDate = LocalDate.MIN;
        IPartnership partnership = null;

        for (IPartnership p : person.getPartnerships()) {
            if (p.getPartnershipDate().isBefore(date) && latestPartnershipDate.isBefore(p.getPartnershipDate())) {
                latestPartnershipDate = p.getPartnershipDate();
                partnership = p;
            }
        }
        return partnership;
    }

    public static Integer numberOfChildrenBirthedBeforeDate(IPerson person, LocalDate y) {

        int count = 0;

        for (IPartnership p : person.getPartnerships()) {
            for (IPerson c : p.getChildren()) {
                if (c.getBirthDate().isBefore(y)) {
                    count++;
                }
            }
        }

        return count;
    }

    public static LocalDate getDateOfNextPostSeparationEvent(IPerson person, LocalDate separationDate) {

        LocalDate earliestDate = null;

        for (IPartnership partnership : person.getPartnerships()) {
            LocalDate date = partnership.getPartnershipDate();
            if (separationDate.isBefore(date)) {

                if (earliestDate == null || date.isBefore(earliestDate)) {
                    earliestDate = date;
                }
            }

            date = partnership.getMarriageDate();

            if (date != null) {
                if (separationDate.isBefore(date)) {

                    if (earliestDate == null || date.isBefore(earliestDate)) {
                        earliestDate = date;
                    }
                }
            }
        }

        if (earliestDate == null) {
            earliestDate = person.getDeathDate();
        }

        return earliestDate;
    }

    // Ensures a person is present in country at the given date
    public static boolean presentOnDate(IPerson person, LocalDate date) {

        LocalDate immigrationDate = person.getImmigrationDate();
        LocalDate emigrationDate = person.getEmigrationDate();

        if(immigrationDate != null && immigrationDate.isAfter(date)) {
            // date is before person arrived in country
            return false;
        } else {
            // not immigrant or already arrived
            if(emigrationDate != null && emigrationDate.isBefore(date)) {
                // date is after person leaves country
                // therefore not present on date
                return false;
            } else {
                // never emigrates or not yet left
                // therefore present on date
                return true;
            }
        }
    }

    public static boolean childOf(IPerson parent, IPerson person) {

        if(person.getParents() == null) return false;
        if(person.getParents().getMalePartner() == parent) return true;
        if(person.getParents().getFemalePartner() == parent) return true;

        return false;

    }
}
