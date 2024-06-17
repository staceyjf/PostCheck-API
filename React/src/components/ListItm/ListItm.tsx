import {
  Box,
  IconButton,
  useTheme,
  ListItem,
  ListItemText,
} from "@mui/material";
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

const ListItm = ({
  id,
  postcode,
  suburbName,
  suburbState,
}: // deleteOnClick,
// handleEdit,
// handleIsComplete,
ListItmProps) => {
  const theme = useTheme();

  return (
    <ListItem
      key={id}
      id={id?.toString()}
      secondaryAction={
        <>
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
        </>
      }
    >
      <Box
        display="flex"
        flexDirection="row"
        alignContent="center"
        justifyContent="space-between"
      >
        <>
          <ListItemText
            primary={postcode}
            sx={{ color: theme.palette.primary.main }}
          />
          <ListItemText
            sx={{ paddingLeft: 3, textTransform: "capitalize" }}
            primary={`${suburbName}, ${suburbState}`}
          />
        </>
      </Box>
    </ListItem>
  );
};

export default ListItm;
