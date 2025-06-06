package pt.ipp.isep.dei.domain;
import java.util.Timer;
import java.util.TimerTask;

public class Simulator {
    private int dataInicio ;
    private int dataFinal;
    private final Scenario scenario;
    private Timer tempoSimulador;
    private boolean isPaused = false;
    private boolean isRunning = false;
    private int dataCurrente;

    public Simulator(int dataInicio, int dataFinal, Scenario scenario) {
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.scenario = scenario;
    }
    public void correrTempo() {
        dataInicio = scenario.getStartYear();
        dataFinal = scenario.getEndYear();

        if (isRunning) return;

        isRunning = true;
        isPaused = false;
        tempoSimulador = new Timer();
        dataCurrente = dataInicio;

        tempoSimulador.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (dataCurrente > dataFinal) {
                    System.out.println("Simulação concluída! Ano final: " + dataFinal);
                    tempoSimulador.cancel();
                    tempoSimulador.purge();
                } else {
                    // Atualiza o ano no Scenario e locomotivas disponíveis
                    scenario.atualizarLocomotivasDisponiveis(dataCurrente);
                    System.out.println("Ano atual da simulação: " + dataCurrente);
                    dataCurrente++;
                }
            }
        }, 0, 1000);
    }

    public void pauseSimulation() {
        if (isRunning && !isPaused) {
            isPaused = true;
            System.out.println("Simulação pausada no ano " + dataCurrente);
        }
    }

    public void resumeSimulation() {
        if (isRunning && isPaused) {
            isPaused = false;
            System.out.println("Simulação retomada do ano " + dataCurrente);
        }
    }
    public int getDataCurrente() {
        return dataCurrente;
    }
    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }

}
