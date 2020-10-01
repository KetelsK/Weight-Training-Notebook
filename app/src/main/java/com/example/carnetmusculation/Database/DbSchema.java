package com.example.carnetmusculation.Database;

public class DbSchema {
    public static final class CarnetsTable{
        public static final String NAME="carnets";
        public static final class cols{
            public static final String UUID ="uuid";
            public static final String NOM="nom";
            public static final String UUIDSeance="uuidseance";
        }
    }
    public static final class ExercicesTable{
        public static final String NAME="exercices";
        public static final class cols{
            public static final String UUID="uuid";
            public static final String NOM="nom";
            public static final String UUIDSEANCE="uuidseance";
        }
    }
    public static final class MesExercicesTable{
        public static final String NAME="mesExercices";
        public static final class cols{
            public static final String UUID="uuid";
            public static final String NOM="nom";
        }
    }
    public static final class MuscleTable{
        public static final String NAME="muscle";
        public static final class cols{
            public static final String UUID="uuid";
            public static final String NOM="nom";
        }
    }
    public static final class SeancesTable {
        public static final String NAME="seances";
        public static final class cols{
            public static final String UUID ="uuid";
            public static final String NOM="nom";
            public static final String UUIDCARNET ="uuidcarnet";
        }
    }

    public static final class PerformanceTable{
        public static final String NAME="performance";
        public static final class cols{
            public static final String UUID ="uuid";
            public static final String DATE="date";
            public static final String POIDS="poids";
            public static final String NBREPS="nbreps";
            public static final String UUIDEXERCICE ="uuidexercice";
        }
    }
}
