package br.com.model;


/**
 *
 * @author Cleber
 */
public class DetalheCC {

    private int id;
    private int chavecc;
    private String nuNF;
    private double vlNF;
    private int qtdVolumes;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChavecc() {
        return chavecc;
    }

    public void setChavecc(int chavecc) {
        this.chavecc = chavecc;
    }

    public String getNuNF() {
        return nuNF;
    }

    public void setNuNF(int nuNF) {
        this.nuNF = "" + nuNF;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQtdVolumes() {
        return qtdVolumes;
    }

    public void setQtdVlumes(int tqtVolumes) {
        this.qtdVolumes = tqtVolumes;
    }

    public double getVlNF() {
        return vlNF;
    }

    public void setVlNF(double vlNF) {
        this.vlNF = vlNF;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + this.chavecc;
        hash = 47 * hash + (this.nuNF != null ? this.nuNF.hashCode() : 0);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.vlNF) ^ (Double.doubleToLongBits(this.vlNF) >>> 32));
        hash = 47 * hash + this.qtdVolumes;
        hash = 47 * hash + (this.status ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DetalheCC other = (DetalheCC) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.chavecc != other.chavecc) {
            return false;
        }
        if ((this.nuNF == null) ? (other.nuNF != null) : !this.nuNF.equals(other.nuNF)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vlNF) != Double.doubleToLongBits(other.vlNF)) {
            return false;
        }
        if (this.qtdVolumes != other.qtdVolumes) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

   
}
