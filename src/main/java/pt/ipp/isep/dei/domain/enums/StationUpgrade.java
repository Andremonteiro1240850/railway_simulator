package pt.ipp.isep.dei.domain.enums;

/**
 * Enum que representa os vários tipos de melhorias que podem ser aplicadas a uma estação ferroviária.
 * Cada melhoria tem um nome de exibição, ano de disponibilidade, custo, e possíveis regras de exclusividade ou substituição.
 */

public enum StationUpgrade {
    TELEGRAPH("Posto Telegráfico", 1830, 10000, "TELEPHONE", null),
    TELEPHONE("Telefone", 1860, 25000, null, "TELEGRAPH"),
    RESTAURANT_SMALL("Restaurante Pequeno", 1850, 5000, null, null),
    RESTAURANT_LARGE("Restaurante Grande", 1850, 20000, "HOTEL_LARGE", null),
    SILO("Silo", 1870, 15000, "LIQUID_STORAGE", null),
    HOTEL_SMALL("Hotel Pequeno", 1880, 10000, null, null),
    HOTEL_LARGE("Hotel Grande", 1880, 30000, "RESTAURANT_LARGE", null),
    CUSTOMS("Alfândega", 1875, 20000, null, null),
    LIQUID_STORAGE("Armazenamento de Líquidos", 1890, 20000, "SILO", null);

    private final String displayName;
    private final int year;
    private final int cost;
    private final String exclusiveWith;
    private final String replaces;


    /**
     * Construtor da enumeração de melhorias de estação.
     *
     * @param displayName   Nome legível da melhoria.
     * @param year          Ano em que a melhoria se torna disponível.
     * @param cost          Custo de implementação da melhoria.
     * @param exclusiveWith Nome de uma melhoria com a qual esta é mutuamente exclusiva.
     * @param replaces      Nome de uma melhoria que esta substitui, se aplicável.
     */

    StationUpgrade(String displayName, int year, int cost, String exclusiveWith, String replaces) {
        this.displayName = displayName;
        this.year = year;
        this.cost = cost;
        this.exclusiveWith = exclusiveWith;
        this.replaces = replaces;
    }


    /**
     * Obtém o nome da melhoria.
     *
     * @return Nome de exibição da melhoria.
     */
    public String getDisplayName() { return displayName; }

    /**
     * Obtém o ano de disponibilidade da melhoria.
     *
     * @return Ano de introdução da melhoria.
     */
    public int getYear() { return year; }

    /**
     * Obtém o custo de implementação da melhoria.
     *
     * @return Custo da melhoria em unidades monetárias.
     */
    public int getCost() { return cost; }

    /**
     * Retorna o nome de outra melhoria com a qual esta é mutuamente exclusiva.
     *
     * @return Nome da melhoria incompatível, ou null se não houver.
     */
    public String getExclusiveWith() { return exclusiveWith; }

    /**
     * Retorna o nome de uma melhoria que esta substitui.
     *
     * @return Nome da melhoria substituída, ou null se não substituir nenhuma.
     */
    public String getReplaces() { return replaces; }
}
