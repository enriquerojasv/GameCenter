package com.androidclase.gamecenter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SenkuBoxAdapter extends ArrayAdapter<SenkuBoxModel> {
    int[][] grid;
    boolean selected = false;

    Context context;
    ViewGroup viewGroup;

    TextView move_counter;
    int moves = 0;
    Chronometer timer;

    public SenkuBoxAdapter(@NonNull Context context, ArrayList<SenkuBoxModel> cellModelArrayList,
                           int[][] selectedGrid, TextView move_count, Chronometer time) {
        super(context, 0, cellModelArrayList);
        this.context = context;

        grid = selectedGrid;
        move_counter = move_count;
        timer = time;
        checkWin();

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        viewGroup = parent;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.senku_cells, parent, false);
        }

        SenkuBoxModel boxModel = getItem(position);

        CardView boxCard = listItemView.findViewById(R.id.boxCard);
        ImageView boxBg = listItemView.findViewById(R.id.boxBg);
        ImageView boxCircle = listItemView.findViewById(R.id.boxCircle);

        //We check the grid size and adjust its dimensions
        if (boxModel.getGridSize() == 8) {
            int boxSize = (int) getContext().getResources().getDimension(R.dimen.box_size_sm);
            boxCard.setLayoutParams(new ViewGroup.LayoutParams(boxSize, boxSize));
        } else if (boxModel.getGridSize() == 9) {
            int boxSize = (int) getContext().getResources().getDimension(R.dimen.box_size_sm2);
            boxCard.setLayoutParams(new ViewGroup.LayoutParams(boxSize, boxSize));
        }

        //We check whether it should be and empty cell or not
        if (boxModel.getCircleId() == 1) {
            boxCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_token));
        } else {
            boxCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_transparent));
        }

        //We check whether it should be a cell (we add a listener if it is) or not
        if (boxModel.getBgId() == 1) {
            boxBg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_bg));

            listItemView.setOnClickListener(view -> checkClick(boxModel, boxCircle));

        } else {
            boxCard.setVisibility(View.INVISIBLE);
        }

        return listItemView;
    }

    private void checkClick(SenkuBoxModel boxModel, ImageView boxCircle) {
        Animation select = AnimationUtils.loadAnimation(boxCircle.getContext(), R.anim.senku_select);
        Animation deselect = AnimationUtils.loadAnimation(boxCircle.getContext(), R.anim.senku_deselect);

        if (boxModel.isPossible()) {
            //We make the move and delete all other suggestions
            checkDirection(boxModel);
            removePossible();
        } else if (selected && boxModel.isSelected()) {
            //Deselect selected cell
            boxCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_token));

            checkDirection(boxModel);

            boxModel.setSelected(false);
            selected = false;

            boxCircle.startAnimation(deselect);

        } else if (!selected && grid[boxModel.getRow()][boxModel.getCol()] == 1) {
            //Select valid cell
            boxCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_selected));

            checkDirection(boxModel);

            boxModel.setSelected(true);
            selected = true;

            boxCircle.startAnimation(select);
        }
    }

    private void checkDirection(SenkuBoxModel boxModel) {
        //Check if the 2 top cells are not out of bounds
        boolean topBounds = boxModel.getRow() - 1 >= 0 && boxModel.getRow() - 2 >= 0;

        //Check if the 2 bottom cells are not out of bounds
        boolean botBounds = boxModel.getRow() + 1 < grid.length && boxModel.getRow() + 2 < grid.length;

        //Check if the 2 left cells are not out of bounds
        boolean leftBounds = boxModel.getCol() - 1 >= 0 && boxModel.getCol() - 2 >= 0;

        //Check if the 2 right cells are not out of bounds
        boolean rightBounds = boxModel.getCol() + 1 < grid.length && boxModel.getCol() + 2 < grid.length;

        if (topBounds) {
            checkPossibles(boxModel, "UP");
        }

        if (botBounds) {
            checkPossibles(boxModel, "DOWN");
        }

        if (leftBounds) {
            checkPossibles(boxModel, "LEFT");
        }

        if (rightBounds) {
            checkPossibles(boxModel, "RIGHT");
        }
    }

    private void checkPossibles(SenkuBoxModel boxModel, String direction) {
        int y = 0;
        int x = 0;

        switch (direction) {
            case "UP":
                y = -1;
                break;
            case "DOWN":
                y = 1;
                break;
            case "LEFT":
                x = -1;
                break;
            case "RIGHT":
                x = 1;
                break;
            default:
                break;
        }

        //We get the value on the grid
        int pos1 = grid[boxModel.getRow() + y][boxModel.getCol() + x];
        int pos2 = grid[boxModel.getRow() + (y * 2)][boxModel.getCol() + (x * 2)];

        //We get the index and using it we get the box
        int index = grid.length * (boxModel.getRow()) + (boxModel.getCol());
        int index1 = grid.length * (boxModel.getRow() + y) + (boxModel.getCol() + x);
        int index2 = grid.length * (boxModel.getRow() + (y * 2)) + (boxModel.getCol() + (x * 2));

        ImageView imageView = viewGroup.getChildAt(index).findViewById(R.id.boxCircle);
        ImageView imageView1 = viewGroup.getChildAt(index1).findViewById(R.id.boxCircle);
        ImageView imageView2 = viewGroup.getChildAt(index2).findViewById(R.id.boxCircle);

        SenkuBoxModel box = getItem(index);
        SenkuBoxModel box1 = getItem(index1);
        SenkuBoxModel box2 = getItem(index2);


        if (pos1 == 1 && pos2 == 2) {
            //We mark all possible moves
            if (!selected) {
                imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_possible));
                Animation possible = AnimationUtils.loadAnimation(imageView2.getContext(), R.anim.senku_possible);
                imageView2.startAnimation(possible);
                box2.setPossible(true);
            } else {
                imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_transparent));
                box2.setPossible(false);
            }
        }

        //We move the token 2 positions a remove de middle one
        if (box2.isSelected() && box.isPossible()) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_token));

            grid[boxModel.getRow()][boxModel.getCol()] = 1;
            grid[boxModel.getRow() + y][boxModel.getCol() + x] = 2;
            grid[boxModel.getRow() + (y * 2)][boxModel.getCol() + (x * 2)] = 2;

            selected = false;

            box.setSelected(false);
            box.setPossible(false);

            box1.setSelected(false);
            box1.setPossible(false);

            box2.setSelected(false);
            box2.setPossible(false);

            Animation move = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.senku_move);
            Animation move2 = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.senku_move2);

            imageView.startAnimation(move);
            imageView1.startAnimation(move2);
            imageView2.startAnimation(move2);

            addMove();
            checkWin();

            move2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imageView1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_transparent));
                    imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.senku_box_transparent));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

    }

    private void removePossible() {
        SenkuBoxModel cell;
        for (int i = 0; i < grid.length * grid.length; i++) {
            cell = getItem(i);
            if (cell.isPossible()) {
                cell.setPossible(false);
                viewGroup.getChildAt(i).findViewById(R.id.boxCircle).setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.senku_box_transparent));
            }
        }
    }

    private void addMove() {
        moves++;
        move_counter.setText(moves + " Moves");
    }

    private void checkWin() {
        int possible_moves = 0;
        int ones = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 1) ones++;
                if (i - 2 >= 0 && j - 2 >= 0 &&
                        i + 2 < grid.length && j + 2 < grid.length) {
                    possible_moves += checking(i, j, 1, 0) +
                            checking(i, j, -1, 0) +
                            checking(i, j, 0, 1) +
                            checking(i, j, 0, -1);


                }
            }
        }

        if (possible_moves == 0) {
            Intent intent = new Intent(getContext(), SenkuGameOver.class);

            if (ones == 1) {
                intent.putExtra("title", "YOU WIN");
                intent.putExtra("bonus", 1.5);
            } else {
                intent.putExtra("title", "YOU LOSE");
                intent.putExtra("bonus", 1.0);
            }

            intent.putExtra("moves", moves);

            long elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
            intent.putExtra("ms", elapsedMillis);

            timer.stop();
            context.startActivity(intent);
        }

    }

    private int checking(int i, int j, int x, int y) {
        int value1 = grid[i][j];
        int value2 = grid[i + x][j + y];
        int value3 = grid[i + (x * 2)][j + (y * 2)];

        if (value1 == 1 && value2 == 1 && value3 == 2) {
            return 1;
        } else {
            return 0;
        }
    }
}

