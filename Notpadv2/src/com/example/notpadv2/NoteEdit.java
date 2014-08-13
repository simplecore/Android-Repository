/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.savedInstanceState
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.notpadv2;

//import com.javacodegeeks.android.tts.TtsActivity;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEdit extends Activity  {

	private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
	TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit);
       
        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
      
        Button confirmButton = (Button) findViewById(R.id.confirm);
       
        Button read=(Button) findViewById(R.id.read);
        
        tts=new TextToSpeech(getApplicationContext(), 
    	      new TextToSpeech.OnInitListener() {
    	      @Override
    	      public void onInit(int status) {
    	         if(status != TextToSpeech.ERROR){
    	        	 tts.setLanguage(Locale.UK);
    	            }				
    	         }
	      });
        
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(NotesDbAdapter.KEY_TITLE);
            String body = extras.getString(NotesDbAdapter.KEY_BODY);
            mRowId = extras.getLong(NotesDbAdapter.KEY_ROWID);
          
            if (title != null) {
                mTitleText.setText(title);
            }
            if (body != null) {
                mBodyText.setText(body);
            }
        }
       
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle bundle = new Bundle();
               
                bundle.putString(NotesDbAdapter.KEY_TITLE, mTitleText.getText().toString());
                bundle.putString(NotesDbAdapter.KEY_BODY, mBodyText.getText().toString());
                if (mRowId != null) {
                    bundle.putLong(NotesDbAdapter.KEY_ROWID, mRowId);
                }
                
                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }
            
        });
        
        read.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				String text = mBodyText.getText().toString();
				if (text!=null && text.length()>0) {
//					Toast.makeText(TtsActivity.this, "Saying: " + text, Toast.LENGTH_LONG).show();
//					tts = new TextToSpeech(this, this);
					tts.speak(text, TextToSpeech.QUEUE_ADD, null);
					Toast.makeText(getApplicationContext(), "Speaking the Selected Note", Toast.LENGTH_SHORT).show();
				}
				
			}
        	
        });
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
//				tts = new TextToSpeech(this, this);
			} 

		}

	}

	
    

}
