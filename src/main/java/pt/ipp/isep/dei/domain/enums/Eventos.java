package pt.ipp.isep.dei.domain.enums;

public enum Eventos {
    THE_IRON_SEED("The Iron Seed", 1830, 1850, "Início das ferrovias nos EUA, abre novas oportunidades de transporte e industrialização."),
    WHISTLE_STOPS_AND_PROMISES("Whistle Stops and Promises", 1904, 1904, "Campanha presidencial de Roosevelt; o jogador deve visitar cidades específicas rapidamente."),
    BIRTH_OF_THE_IRON_HORSE("Birth of the Iron Horse", 1835, 1850, "Expansão ferroviária no Reino Unido durante a Revolução Industrial."),
    EXCESS_ON_THE_ORIENT_EXPRESS("Excess on the Orient Express", 1850, 1883, "Construção da rota Paris–Istambul, com foco em rotas de passageiros de luxo."),
    MOTHER_RUSSIA("Mother Russia", 1941, 1945, "Transporte de tropas e suprimentos durante a invasão alemã na Segunda Guerra Mundial."),
    WHEN_WALLS_COME_DOWN("When Walls Come Down", 1989, 1990, "Queda do Muro de Berlim; fusão de sistemas econômicos e redes ferroviárias."),
    REMEMBERING_AMERICA("Remembering America", 2039, 2050, "Reconstrução dos EUA após colapso; foco em restabelecimento de infraestrutura."),
    THE_CHUNNEL("The Chunnel", 1990, 1994, "Construção do Túnel da Mancha; conecta Inglaterra e França por ferrovia subterrânea."),
    NORTH_TO_ALASKA("North to Alaska", 1930, 1940, "Ferrovia em região inóspita durante a Grande Depressão; transporte de recursos."),
    DILEMMA_DOWN_UNDER("Dilemma Down Under", 1957, 1965, "Desenvolvimento ferroviário australiano sob pressão econômica e política.");

    private final String nome;
    private final int dataInicio;
    private final int dataFim;
    private final String efeitoNoJogo;

    Eventos(String nome, int dataInicio, int dataFim, String efeitoNoJogo) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.efeitoNoJogo = efeitoNoJogo;
    }

    public String getNome() {
        return nome;
    }

    public int getDataInicio() {
        return dataInicio;
    }

    public int getDataFim() {
        return dataFim;
    }

    public String getEfeitoNoJogo() {
        return efeitoNoJogo;
    }

    @Override
    public String toString() {
        return nome + " (" + dataInicio + "–" + dataFim + "): " + efeitoNoJogo;
    }
}
