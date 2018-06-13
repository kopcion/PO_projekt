package Snake;

/**
 * Created by kopcion on 02.06.18.
 */
public class Control {
    protected Snake snake;
    protected Database database;
    protected GameMode gameMode;
    protected Ranking ranking;

    public Control(){}
    public void setRanking(Ranking ranking){
        this.ranking = ranking;
    }
    public void setSnake(Snake snake){
        this.snake = snake;
    }
    public void setDatabase(Database database){
        this.database = database;
    }

    public void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
    }

    public Ranking getRanking(){
        return ranking;
    }
    public Snake getSnake(){
        return snake;
    }
    public Database getDatabase(){
        return database;
    }
    public GameMode getGameMode(){
        return gameMode;
    }
}
