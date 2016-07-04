package com.virtualchemygames.test3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class GameEngine extends ApplicationAdapter implements InputProcessor {
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private ModelBuilder modelBuilder;
	private Model model;
	private ModelInstance modelInstance;
	private Environment environment;
	
	@Override
	public void create () {
		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(3f, 3f, 3f);
		camera.lookAt(0f, 0f, 0f);
		camera.near = -0.1f;
		camera.far = 300f;

		modelBatch = new ModelBatch();
		modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(2f, 2f, 2f, new Material(ColorAttribute.createDiffuse(Color.BLUE)), VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		modelInstance = new ModelInstance(model, 0, 0, 0);
		environment = new Environment();
		environment.set(new ColorAttribute((ColorAttribute.AmbientLight), 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

		camera.update();
		modelBatch.begin(camera);
		modelBatch.render(modelInstance, environment);
		modelBatch.end();
	}

	public boolean keyDown(int keycode){
		if(keycode == Input.Keys.LEFT)
			camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), 10f);
		if(keycode == Input.Keys.RIGHT)
			camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), -10f);
		return true;
	}

	public boolean keyUp(int keycode){
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
