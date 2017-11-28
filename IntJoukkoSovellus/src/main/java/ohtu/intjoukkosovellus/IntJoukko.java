
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int OLETUSKAPASITEETTI = 5;
    public final static int OLETUSKASVATUS = 5;

    private int kasvatuskoko;
    private int[] lukujono;
    private int alkioidenLkm;

    public IntJoukko() {
        alustaLukujono(OLETUSKAPASITEETTI);
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti liian pieni");
        }

        alustaLukujono(kapasiteetti);
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti liian pieni");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kasvatuskoko liian pieni");
        }

        alustaLukujono(kapasiteetti);
        this.kasvatuskoko = kasvatuskoko;
    }

    private void alustaLukujono(int kapasiteetti) {
        lukujono = new int[kapasiteetti];
        for (int i = 0; i < lukujono.length; i++) {
            setLukujono(i, 0);
        }
        alkioidenLkm = 0;
    }

    private void setLukujono(int index, int luku) {
        lukujono[index] = luku;
    }

    private void lisaaLukujonoon(int index, int luku) {
        setLukujono(index, luku);
        alkioidenLkm++;
    }

    private void suoritaLisays(int luku) {
        lisaaLukujonoon(alkioidenLkm, luku);

        if (alkioidenLkm % lukujono.length == 0) {
            int[] alkuperainenLukujono = lukujono;
            lukujono = new int[alkioidenLkm + kasvatuskoko];
            kopioiLukujono(alkuperainenLukujono, lukujono);
        }
    }

    public boolean lisaa(int luku) {
        if (alkioidenLkm == 0) {
            lisaaLukujonoon(0, luku);
            return true;
        }

        if (!kuuluu(luku)) {
            suoritaLisays(luku);
            return true;
        }

        return false;
    }

    private void kopioiLukujono(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }
    }

    public boolean kuuluu(int luku) {
        return etsiPoistettavaKohta(luku) > -1;
    }

    private int etsiPoistettavaKohta(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                return i;
            }
        }

        return -1;
    }

    private void suoritaPoisto(int poistettavaKohta) {
        int tmp;
        lukujono[poistettavaKohta] = 0;
        for (int j = poistettavaKohta; j < alkioidenLkm - 1; j++) {
            tmp = lukujono[j];
            lukujono[j] = lukujono[j + 1];
            lukujono[j + 1] = tmp;
        }
        alkioidenLkm--;
    }

    public boolean poista(int luku) {
        int poistettavaKohta = etsiPoistettavaKohta(luku);

        if (luku == -1) {
            return false;
        }

        suoritaPoisto(poistettavaKohta);
        return true;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + lukujono[0] + "}";
        }

        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += lukujono[i] + ", ";
        }
        return tuotos + lukujono[alkioidenLkm - 1] + "}";
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujono[i];
        }
        return taulu;
    }

    public static void lisaaTauluun(int[] taulu, IntJoukko joukko) {
        for (int i = 0; i < taulu.length; i++) {
            joukko.lisaa(taulu[i]);
        }
    }

    public static void poistaTaulusta(int[] taulu, IntJoukko joukko) {
        for (int i = 0; i < taulu.length; i++) {
            joukko.poista(i);
        }
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        IntJoukko.lisaaTauluun(aTaulu, joukko);
        IntJoukko.lisaaTauluun(bTaulu, joukko);

        return joukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    joukko.lisaa(bTaulu[j]);
                }
            }
        }

        return joukko;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        IntJoukko.lisaaTauluun(aTaulu, joukko);
        IntJoukko.poistaTaulusta(bTaulu, joukko);

        return joukko;
    }
}