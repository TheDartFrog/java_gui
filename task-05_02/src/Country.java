public enum Country {
    PL {
        @Override
        public String toString() {
            return "Polska";
        }
    },
    DE{
        @Override
        public String toString() {
            return "Deutschland";
        }
    },
    NL{
        @Override
        public String toString() {
            return "Nederland";
        }
    }
}
