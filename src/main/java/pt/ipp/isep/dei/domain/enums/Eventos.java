package pt.ipp.isep.dei.domain.enums;

/**
 * Represents predefined historical events that affect gameplay in different ways,
 * such as economic conditions, war efforts, or technological advances.
 * Each event has a name, start and end year, and a gameplay effect.
 */
public enum Eventos {

    THE_IRON_SEED("The Iron Seed", 1830, 1850,
            "Início das ferrovias nos EUA, abre novas oportunidades de transporte e industrialização."),


    WHISTLE_STOPS_AND_PROMISES("Whistle Stops and Promises", 1904, 1904,
            "Campanha presidencial de Roosevelt; o jogador deve visitar cidades específicas rapidamente."),

    BIRTH_OF_THE_IRON_HORSE("Birth of the Iron Horse", 1835, 1850,
            "Expansão ferroviária no Reino Unido durante a Revolução Industrial."),

    EXCESS_ON_THE_ORIENT_EXPRESS("Excess on the Orient Express", 1850, 1883,
            "Construção da rota Paris–Istambul, com foco em rotas de passageiros de luxo."),

    MOTHER_RUSSIA("Mother Russia", 1941, 1945,
            "Transporte de tropas e suprimentos durante a invasão alemã na Segunda Guerra Mundial."),

    WHEN_WALLS_COME_DOWN("When Walls Come Down", 1989, 1990,
            "Queda do Muro de Berlim; fusão de sistemas econômicos e redes ferroviárias."),

    REMEMBERING_AMERICA("Remembering America", 2039, 2050,
            "Reconstrução dos EUA após colapso; foco em restabelecimento de infraestrutura."),

    THE_CHUNNEL("The Chunnel", 1990, 1994,
            "Construção do Túnel da Mancha; conecta Inglaterra e França por ferrovia subterrânea."),

    NORTH_TO_ALASKA("North to Alaska", 1930, 1940,
            "Ferrovia em região inóspita durante a Grande Depressão; transporte de recursos."),

    DILEMMA_DOWN_UNDER("Dilemma Down Under", 1957, 1965,
            "Desenvolvimento ferroviário australiano sob pressão econômica e política.");

    private final String nome;
    private final int dataInicio;
    private final int dataFim;
    private final String efeitoNoJogo;

    /**
     * Constructs a historical event.
     *
     * @param nome          the name of the event
     * @param dataInicio    the year the event starts
     * @param dataFim       the year the event ends
     * @param efeitoNoJogo  the description of the gameplay effect of the event
     */
    Eventos(String nome, int dataInicio, int dataFim, String efeitoNoJogo) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.efeitoNoJogo = efeitoNoJogo;
    }

    /**
     * Gets the name of the event.
     *
     * @return the name of the event
     */
    public String getNome() {
        return nome;
    }

    /**
     * Gets the year the event starts.
     *
     * @return the start year
     */
    public int getDataInicio() {
        return dataInicio;
    }

    /**
     * Gets the year the event ends.
     *
     * @return the end year
     */
    public int getDataFim() {
        return dataFim;
    }

    /**
     * Gets the effect of the event in the game.
     *
     * @return the gameplay effect description
     */
    public String getEfeitoNoJogo() {
        return efeitoNoJogo;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return a formatted string with name, duration and effect
     */
    @Override
    public String toString() {
        return nome + " (" + dataInicio + "–" + dataFim + "): " + efeitoNoJogo;
    }
}
