import java.util.ArrayList;
import java.util.List;

/**
 * Classe que implementa um {@link Gene}.
 *
 * @author <a href="mailto:yuri.arend@acad.pucrs.br">yuri.arend</a>
 * @since 15/09/2018 07:06:00
 */
public class Gene {
    private String locus;
    private long begin;
    private long end;
    private List<Character> bases;

    public Gene(String locus, long begin, long end, List<Character> bases) {
        super();
        this.locus = locus;
        this.begin = begin;
        this.end = end;
        this.bases = bases;
    }

    public List<String> getTresCincoUm() {
        return decodeReverse(0);
    }

    public List<String> getTresCincoDois() {
        return decodeReverse(1);
    }

    public List<String> getTresCincoTres() {
        return decodeReverse(2);
    }

    public List<String> getCincoTresUm() {
        return decode(0);
    }

    public List<String> getCincoTresDois() {
        return decode(1);
    }

    public List<String> getCincoTresTres() {
        return decode(2);
    }

    public String getLocus() {
        return locus;
    }

    public long getBegin() {
        return begin;
    }

    public long getEnd() {
        return end;
    }

    public List<Character> getBases() {
        return bases;
    }

    public List<String> decode(int index) {
        List<String> codons = new ArrayList<>();
        int count = index;
        int limite = 0;
        switch (index) {
            case 0:
                limite = 3;
                break;
            case 1:
                limite = 4;
                break;
            case 2:
                limite = 5;
                break;
        }
        for (int i = 0; i <= this.bases.size() - limite; i += 3) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                String a = this.bases.get(count).toString();
                count++;
                sb.append(a);
            }
            codons.add(AminoacidTable.getInstance().getAminoacid(sb.toString()));
        }

        return codons;
    }

    public List<String> decodeReverse(int index) {
        List<String> codons = new ArrayList<>();
        int count = this.bases.size() - index - 1;
        for (int i = this.bases.size() - 1; i > 3; i -= 3) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                String a = this.bases.get(count).toString();
                count--;
                sb.append(a);
            }
            codons.add(AminoacidTable.getInstance().getAminoacid(sb.toString()));
        }
        return codons;
    }

    public List<String> getRightSequence() {

        List<String> lista1 = decode(0);
        List<String> lista2 = decode(1);
        List<String> lista3 = decode(2);
        List<String> lista4 = decodeReverse(0);
        List<String> lista5 = decodeReverse(1);
        List<String> lista6 = decodeReverse(2);
        List<String> theChosenOne = lista1;
        if (countRightSequence(lista2) > countRightSequence(theChosenOne)) {
            theChosenOne = lista2;
        }
        if (countRightSequence(lista3) > countRightSequence(theChosenOne)) {
            theChosenOne = lista3;
        }
        if (countRightSequence(lista4) > countRightSequence(theChosenOne)) {
            theChosenOne = lista4;
        }
        if (countRightSequence(lista5) > countRightSequence(theChosenOne)) {
            theChosenOne = lista5;
        }
        if (countRightSequence(lista6) > countRightSequence(theChosenOne)) {
            theChosenOne = lista6;
        }

        return theChosenOne;

    }

    public int countRightSequence(List<String> lista) {
        boolean contagemIniciada = false;
        int counter = 0;
        for (String amino : lista) {
            if (contagemIniciada) {
                counter++;
            } else if ("Met".equalsIgnoreCase(amino)) {
                contagemIniciada = true;
            } else if ("Stop".equalsIgnoreCase(amino)) {
                return counter;
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        return "Gene [locus=" + locus + ", begin=" + begin + ", end=" + end + ", bases=" + bases + "]";
    }
}
