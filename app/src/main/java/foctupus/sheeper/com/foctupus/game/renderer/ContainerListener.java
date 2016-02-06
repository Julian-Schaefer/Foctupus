package foctupus.sheeper.com.foctupus.game.renderer;

/**
 * Created by schae on 06.02.2016.
 */
public interface ContainerListener {
    public abstract void onRevalidate(Container container);
    public abstract void onLoaded(Container container);
    public abstract void onFinished(Container container);
}
