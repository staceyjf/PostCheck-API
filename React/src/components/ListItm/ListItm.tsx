import { Box, IconButton, useTheme, TableRow, TableCell } from "@mui/material";
import { Delete as DeleteIcon, Edit as EditIcon } from "@mui/icons-material";

// define the props
interface ListItmProps {
  id: number | undefined;
  postcode: string;
  suburbName: string;
  suburbState: string;
  // deleteOnClick: (id: number | undefined) => void;
  // handleEdit: (id: number | undefined) => void;
  // handleIsComplete: (id: number | undefined, isComplete: boolean) => void;
}

const ListItm = ({ id, postcode, suburbName, suburbState }: ListItmProps) => {
  const theme = useTheme();

  return (
    <>
      <TableCell sx={{ color: theme.palette.primary.main }}>
        {postcode}
      </TableCell>
      <TableCell sx={{ textTransform: "capitalize" }}>
        {`${suburbName}, ${suburbState}`}
      </TableCell>
      <TableCell>
        <Box
          display="flex"
          flexDirection="row"
          alignContent="center"
          justifyContent="flex-end"
          columnGap="0.5em"
        >
          <IconButton
            edge="end"
            aria-label="delete"
            onClick={(e) => console.log("hello")}
          >
            <DeleteIcon />
          </IconButton>
          <IconButton
            edge="end"
            aria-label="edit"
            onClick={(e) => console.log("hello")}
          >
            <EditIcon />
          </IconButton>
        </Box>
      </TableCell>
    </>
  );
};

export default ListItm;
