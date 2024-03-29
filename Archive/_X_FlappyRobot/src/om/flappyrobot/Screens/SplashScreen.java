package om.flappyrobot.Screens;

import om.flappyrobot.Engine.FRGame;
import om.flappyrobot.Helpers.AssetLoader;
import om.flappyrobot.TweenAccessors.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen extends AbstractScreen {

	private static final TweenManager manager = new TweenManager();
	private final FRGame game;
	private Sprite sprite;

	public SplashScreen(FRGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		sprite = new Sprite(AssetLoader.logo);
		sprite.setColor(1, 1, 1, 0);

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		float scale = (width * .7f) / sprite.getWidth();

		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2) - (sprite.getHeight() / 2));
		setupTween();
	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				game.setScreen(new GameScreen());
			}
		};

		Tween.to(sprite, SpriteAccessor.ALPHA, .8f).target(1).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .8f)
				.setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
	}

	@Override
	public void render(float delta) {
		SpriteBatch batcher = new SpriteBatch();
		manager.update(delta);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batcher.begin();
		sprite.draw(batcher);
		batcher.end();
	}
}
