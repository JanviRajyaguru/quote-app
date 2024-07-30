package com.example.quote;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private Button generateQuoteButton;
    private ImageButton whatsappButton;
    private ImageButton instagramButton;
    private ImageButton gmailButton;

    private String[] quotes = {
            "Happiness is the key to success.",
            "Believe you can, you're halfway there.",
            "Create your future, don't predict.",
            "Purpose of life: be happy.",
            "Regret missed chances, not failures.",
            "Self-love is not selfish.",
            "Life is 90% your reaction.",
            "A smile is universal welcome.",
            "Enjoy little things, they're big.",
            "Happiness is a choice, not chance."

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.quoteTextView);
        generateQuoteButton = findViewById(R.id.generateQuoteButton);
        whatsappButton = findViewById(R.id.whatsappButton);
        instagramButton = findViewById(R.id.instagramButton);
        gmailButton = findViewById(R.id.gmailButton);

        generateQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomQuote();
            }
        });

        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnWhatsApp();
            }
        });

        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote("com.instagram.android");
            }
        });

        gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote("com.google.android.gm");
            }
        });
    }

    private void generateRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.length);
        quoteTextView.setText(quotes[index]);
    }

    private void shareOnWhatsApp() {
        String quote = quoteTextView.getText().toString();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, quote);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");

        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            // WhatsApp is not installed
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, quote);
            startActivity(Intent.createChooser(intent, "Share quote using"));
        }
    }

    private void shareQuote(String packageName) {
        String quote = quoteTextView.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote);
        shareIntent.setType("text/plain");
        shareIntent.setPackage(packageName);

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle the error if the app is not installed
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, quote);
            startActivity(Intent.createChooser(intent, "Share quote using"));
        }
    }

    // Optionally, add this method if you need to send a message to a specific WhatsApp chat
    private void shareOnWhatsAppWithNumber(String phoneNumber) {
        String quote = quoteTextView.getText().toString();
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(quote);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            // WhatsApp is not installed
            Intent fallbackIntent = new Intent(Intent.ACTION_SEND);
            fallbackIntent.setType("text/plain");
            fallbackIntent.putExtra(Intent.EXTRA_TEXT, quote);
            startActivity(Intent.createChooser(fallbackIntent, "Share quote using"));
        }
    }
}
